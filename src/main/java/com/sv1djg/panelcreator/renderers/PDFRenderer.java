/*
    PanelCreator - A program to quickly generate front panels and drilling templates for your DIY projects.
    Copyright (C) 2019-2021, Nick Tsakonas (SV1DJG)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
import java.util.List;

class PDFRenderer implements FileRenderer {

    final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
    final float MM_TO_UNITS = 1 / (10 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;
    //TODO:: this works against cropping, now I need to see how to do it properly
    final float SAFETY_MARGIN = 25;

    private PDDocument document;
    private PDPageContentStream contentStream;
    private UnitType inputUnit;

    // font type 1 = PDType1Font.COURIER / COURIER_BOLD
    // font type 2 = PDType1Font.COURIER_OBLIQUE / COURIER_BOLD_OBLIQUE
    // font type 3 = PDType1Font.TIMES_ROMAN / TIMES_BOLD
    // font type 4 = PDType1Font.TIMES_ITALIC / TIMES_BOLD_ITALIC
    // font type 5 = PDType1Font.HELVETICA / HELVETICA_BOLD
    // font type 6 = PDType1Font.HELVETICA_OBLIQUE / HELVETICA_BOLD_OBLIQUE
    private final PDType1Font[][] FONTS = {
            {PDType1Font.COURIER, PDType1Font.COURIER_BOLD},
            {PDType1Font.COURIER_OBLIQUE, PDType1Font.COURIER_BOLD_OBLIQUE},
            {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD},
            {PDType1Font.TIMES_ITALIC, PDType1Font.TIMES_BOLD_ITALIC},
            {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD},
            {PDType1Font.HELVETICA_OBLIQUE, PDType1Font.HELVETICA_BOLD_OBLIQUE},
    };

    @Override
    public OutputRenderer.Operations operations() {

        return new OutputRenderer.Operations() {
            @Override
            public void addText(float x, float y, String text, int fontType, int fontSize, boolean bold, boolean centerAlign) {
                try {
                    contentStream.beginText();
                    PDFont font = getFont(fontType, bold);
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
            public void drawCircle(float centerX, float centerY, float diameter, float width) {
                try {
                    List<BezierPaths.BezierControlPoints> controlPoints = BezierPaths.createCircle(centerX, centerY, diameter / 2.0f);
                    drawBezierPath(controlPoints, width);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void drawArc(float centerX, float centerY, float radius, float startAngle, float endAngle, float width) {
                try {
                    List<BezierPaths.BezierControlPoints> controlPoints = BezierPaths.createArc(centerX, centerY, radius, startAngle, endAngle);
                    drawBezierPath(controlPoints, width);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void drawBezierPath(List<BezierPaths.BezierControlPoints> controlPoints, float width) throws IOException {
                contentStream.setLineWidth(toPDFUnits(width));
                for (BezierPaths.BezierControlPoints controlPoint : controlPoints) {
                    drawBezierCurve(controlPoint);
                }
                contentStream.stroke();
            }

            private void drawBezierCurve(BezierPaths.BezierControlPoints controlPoint) throws IOException {
                //NOTE:: this method is not doing the stroke, the caller has to do it
                contentStream.moveTo(toPDFUnits(controlPoint.x1), toPDFUnits(controlPoint.y1));
                contentStream.curveTo(toPDFUnits(controlPoint.x2), toPDFUnits(controlPoint.y2),
                        toPDFUnits(controlPoint.x3), toPDFUnits(controlPoint.y3),
                        toPDFUnits(controlPoint.x4), toPDFUnits(controlPoint.y4));
            }

            @Override
            public void drawRectangle(float bottomLeftX, float bottomLeftY, float width, float height, float lineWidth) {
                try {
                    // // maybe in the future I will support colour
                    // contentStream.setNonStrokingColor(Color.RED);
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

    private PDFont getFont(int fontType, boolean bold) {
        if (fontType < 1 || fontType > FONTS.length)
            throw new IllegalArgumentException("There are only " + FONTS.length + " fonts available. Could not find font [" + fontType + "]");
        return FONTS[fontType - 1][bold ? 1 : 0];
    }

    @Override
    public void prepare(PageSize pageSize, PageOrientation pageOrientation, UnitType inputUnit) throws IOException {
        // Create a document and add a page to it
        this.inputUnit = inputUnit;
        document = new PDDocument();
        PDPage page = new PDPage(getPdfPageSize(pageSize));
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        if (pageOrientation == PageOrientation.LANDSCAPE)
            setPageToLandscape(page);
        addSafetyMargins(page);
    }

    private void addSafetyMargins(PDPage page) throws IOException {
        PDRectangle pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();
        contentStream.transform(Matrix.getTranslateInstance(SAFETY_MARGIN, SAFETY_MARGIN));
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
