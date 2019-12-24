package com.sv1djg.panelcreator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class OutputRenderer {
    public interface Operations {
        void addText(float x, float y, int fontSize, String text);

        void drawLine(float startX, float startY, float endX, float endY);
    }

    private interface FileRenderer {
        Operations operations();

        void prepare() throws IOException;

        void save() throws IOException;
    }

    private static class PDFRenderer implements FileRenderer {
        final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
        final float MM_TO_UNITS = 1 / (10 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;

        private PDDocument document;
        private PDPageContentStream contentStream;

        @Override
        public Operations operations() {

            return new Operations() {
                @Override
                public void addText(float x, float y, int fontSize, String text) {
                    try {
                        contentStream.beginText();
                        PDFont font = PDType1Font.COURIER;
                        contentStream.setFont(font, fontSize);
                        contentStream.newLineAtOffset(x * MM_TO_UNITS, y * MM_TO_UNITS);
                        contentStream.showText(text);
                        contentStream.endText();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void drawLine(float startX, float startY, float endX, float endY) {

                }
            };
        }

        @Override
        public void prepare() throws IOException {
            // Create a document and add a page to it
            document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            // use the page in landscape
            //https://stackoverflow.com/a/37554006/2750791
            document.addPage(page);
            // Start a new content stream which will "hold" the to be created content
            contentStream = new PDPageContentStream(document, page);
            // printPageInfo(page);
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

    public void processTemplate(PanelTemplate template) throws IOException {
        FileRenderer renderer = new PDFRenderer();
        renderer.prepare();
        template.render(renderer.operations());
        renderer.save();
    }


}
