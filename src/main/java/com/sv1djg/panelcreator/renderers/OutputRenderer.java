package com.sv1djg.panelcreator.renderers;

import com.sv1djg.panelcreator.PanelTemplate;
import com.sv1djg.panelcreator.panelitems.UnitType;

import java.io.IOException;

public class OutputRenderer {
    public interface Operations {
        void addText(float x, float y, String text, int fontType, int fontSize, boolean bold, boolean centerAlign);

        void drawLine(float startX, float startY, float endX, float endY, float width);

        void drawCircle(float centerX, float centerY, float radius, float width);

        void drawRectangle(float bottomLeftX, float bottomLeftY, float width, float height, float lineWidth);

    }

    // the supported page size is an A4 page
    private final PageSize pageSize = PageSize.A4;

    public void processTemplate(PanelTemplate template) throws IOException {
        PageOrientation pageOrientation = getPageOrientationFromTemplateSize(template.getWidth(), template.getHeight(), template.getUnits());
        FileRenderer renderer = FileRendererFactory.getRenderer();
        renderer.prepare(pageSize, pageOrientation, template.getUnits());
        template.render(renderer.operations());
        renderer.save();
    }

    private PageOrientation getPageOrientationFromTemplateSize(float width, float height, UnitType units) {
        // the engine internally uses mm
        if (units == UnitType.INCHES) {
            width = UnitConverter.inchesToMM(width);
            height = UnitConverter.inchesToMM(height);
        }

        PageOrientation pageOrientation = fitsInPage(width, height, pageSize);
        if (pageOrientation == PageOrientation.NONE)
            throw new IllegalArgumentException("The template cannot fit in an A4 page");
        return pageOrientation;

    }

    private PageOrientation fitsInPage(float width, float height, PageSize pageSize) {
        // try in portrait 
        if (width <= pageSize.widthMM && height <= pageSize.heightMM)
            return PageOrientation.PORTRAIT;
        // ..and then in landscape
        if (width <= pageSize.heightMM && height <= pageSize.widthMM)
            return PageOrientation.LANDSCAPE;
        return PageOrientation.NONE;
    }


}
