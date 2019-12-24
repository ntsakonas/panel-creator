package com.sv1djg.panelcreator.renderers;

import com.sv1djg.panelcreator.panelitems.UnitType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;

class PDFRenderer implements FileRenderer {
    final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
    final float MM_TO_UNITS = 1 / (10 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;

    private PDDocument document;
    private PDPageContentStream contentStream;
    private UnitType inputUnit;

    @Override
    public OutputRenderer.Operations operations() {

        return new OutputRenderer.Operations() {
            @Override
            public void addText(float x, float y, int fontSize, String text, boolean centerAlign) {
                try {
                    contentStream.beginText();
                    PDFont font = PDType1Font.COURIER;
                    contentStream.setFont(font, fontSize);
                    if (centerAlign) {
                        float textWidth = font.getStringWidth(text) / 1000.0f * fontSize;
                        float startX = toPDFUnits(x) - textWidth / 2.0f;
                        contentStream.newLineAtOffset(startX, toPDFUnits(y));
                    } else {
                        contentStream.newLineAtOffset(toPDFUnits(x), toPDFUnits(y));
                    }
                    contentStream.showText(text);
                    contentStream.endText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void drawLine(float startX, float startY, float endX, float endY, float width) {
                try {
                    contentStream.setLineWidth(toPDFUnits(width));
                    // maybe in the future I will support colour
                    // contentStream.setNonStrokingColor(Color.GRAY)
                    // contentStream.setLineJoinStyle(0);
                    contentStream.setLineCapStyle(2);
                    contentStream.moveTo(toPDFUnits(startX), toPDFUnits(startY));
                    contentStream.lineTo(toPDFUnits(endX), toPDFUnits(endY));
                    contentStream.stroke();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void drawCircle(float centerX, float centerY, float radius, float width) {
                try {
                    // approximate circle using bezier curves
                    // http://spencermortensen.com/articles/bezier-circle/
                    // using the final solution
                    // P_0 = (0,1), P_1 = (c,1), P_2 = (1,c), P_3 = (1,0)
                    // P_0 = (1,0), P_1 = (1,-c), P_2 = (c,-1), P_3 = (0,-1)
                    // P_0 = (0,-1), P_1 = (-c,-1), P_3 = (-1,-c), P_4 = (-1,0)
                    // P_0 = (-1,0), P_1 = (-1,c), P_2 = (-c,1), P_3 = (0,1)
                    // with c = 0.551915024494
                    final float k = 0.551915024494f;
                    final float arcPoint = k * radius;
                    contentStream.setLineWidth(toPDFUnits(width));
                    contentStream.moveTo(toPDFUnits(centerX), toPDFUnits(centerY + radius));
                    contentStream.curveTo(toPDFUnits(centerX + arcPoint), toPDFUnits(centerY + radius),
                            toPDFUnits(centerX + radius), toPDFUnits(centerY + arcPoint),
                            toPDFUnits(centerX + radius), toPDFUnits(centerY));
                    contentStream.curveTo(toPDFUnits(centerX + radius), toPDFUnits(centerY - arcPoint),
                            toPDFUnits(centerX + arcPoint), toPDFUnits(centerY - radius),
                            toPDFUnits(centerX), toPDFUnits(centerY - radius));
                    contentStream.curveTo(toPDFUnits(centerX - arcPoint), toPDFUnits(centerY - radius),
                            toPDFUnits(centerX - radius), toPDFUnits(centerY - arcPoint),
                            toPDFUnits(centerX - radius), toPDFUnits(centerY));
                    contentStream.curveTo(toPDFUnits(centerX - radius), toPDFUnits(centerY + arcPoint),
                            toPDFUnits(centerX - arcPoint), toPDFUnits(centerY + radius),
                            toPDFUnits(centerX), toPDFUnits(centerY + radius));
                    contentStream.stroke();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void drawRectangle(float bottomLeftX, float bottomLeftY, float width, float height, float lineWidth) {
                try {
                    // maybe in the future I will support colour
                    //contentStream.setNonStrokingColor(Color.RED);
                    contentStream.addRect(toPDFUnits(bottomLeftX), toPDFUnits(bottomLeftY), toPDFUnits(width), toPDFUnits(height));
                    contentStream.fill();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private float toPDFUnits(float input) {
        if (inputUnit == UnitType.INCHES)
            input = UnitConverter.inchesToMM(input);
        return input * MM_TO_UNITS;
    }

    @Override
    public void prepare(PageSize pageSize, PageOrientation pageOrientation, UnitType inputUnit) throws IOException {
        // Create a document and add a page to it
        this.inputUnit = inputUnit;
        document = new PDDocument();
        PDPage page = new PDPage(getPdfPageSize(pageSize));
        document.addPage(page);
        // Start a new content stream which will "hold" the to be created content
        contentStream = new PDPageContentStream(document, page);
        if (pageOrientation == PageOrientation.LANDSCAPE)
            setPageToLandscape(page);
        // printPageInfo(page);
    }

    private void setPageToLandscape(PDPage page) throws IOException {
        // use the page in landscape
        //https://stackoverflow.com/a/37554006/2750791
        PDRectangle pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();
        page.setRotation(90);
        contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
    }

    private PDRectangle getPdfPageSize(PageSize pageSize) {
        switch (pageSize) {
            case A4:
            default:
                return PDRectangle.A4;
        }
    }

    private void printPageInfo(PDPage page) {
        //https://stackoverflow.com/a/21523407/2750791
        final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
        final float MM_TO_UNITS = 1 / (10 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;

        System.out.println("PDF Page info");
        PDRectangle cropBox = page.getCropBox();
        System.out.println("width=" + cropBox.getWidth() + ",height=" + cropBox.getHeight());
        // this will print out the A4 size (21.0 x 29.7cm)
        System.out.println("width=" + cropBox.getWidth() / MM_TO_UNITS + ",height=" + cropBox.getHeight() / MM_TO_UNITS);
        System.out.println("lower left x =" + cropBox.getLowerLeftX() + ",lower left Y =" + cropBox.getLowerLeftY());
        System.out.println("upper right x =" + cropBox.getUpperRightX() + ",upper left Y =" + cropBox.getUpperRightY());
    }

    @Override
    public void save() throws IOException {
        // Make sure that the content stream is closed:
        contentStream.close();
        // Save the results and ensure that the document is properly closed:
        document.save("template.pdf");
        document.close();
    }
}
