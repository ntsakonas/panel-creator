package com.sv1djg.panelcreator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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
                        contentStream.newLineAtOffset(x, y);
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
            PDPage page = new PDPage();
            // page.setRotation(90);
            document.addPage(page);
            // Start a new content stream which will "hold" the to be created content
            contentStream = new PDPageContentStream(document, page);
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
