package com.sv1djg.panelcreator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class TemplateRenderer {
    public void render(PanelTemplate template) throws IOException {
        // create the target pdf object that will receive the output
        // add methods in the OutputRenderer object as required by the renderers
        // chose a better name

        someTests();
        OutputRenderer outputRenderer = new OutputRenderer();
        outputRenderer.processTemplate(template);
        // template.render(outputRenderer);

    }

    private void someTests()  {
        try {
            // test1();
            test2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void test2() throws IOException {
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        // page.setRotation(90);
        document.addPage( page );

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.COURIER;

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
        for (int i=0;i<20;i++) {
            contentStream.beginText();
            contentStream.setFont(font, 12+i);
            contentStream.newLineAtOffset(10+i*5, 10+i*5);
            contentStream.showText("Hello World");
            contentStream.endText();
        }

        // Make sure that the content stream is closed:
        contentStream.close();

        // Save the results and ensure that the document is properly closed:
        document.save( "Hello World.pdf");
        document.close();

    }

    private void test1() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();
        contentStream.showText("Hello World");
        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();
    }
}
