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

import com.sv1djg.panelcreator.PanelTemplate;
import com.sv1djg.panelcreator.panelitems.UnitType;

import java.io.IOException;

public class OutputRenderer {
    public interface Operations {
        void addText(float x, float y, String text, int fontType, int fontSize, boolean bold, boolean centerAlign);

        void drawLine(float startX, float startY, float endX, float endY, float width);

        void drawCircle(float centerX, float centerY, float radius, float width);

        void drawArc(float centerX, float centerY, float radius, float startAngle, float endAngle, float width);

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
        // TODO:: this works against cropping, now I need to see how to do it properly
        // the rednerer has safery margin 25 units -> 8mm
        // I need to make the renderer return safety margin in mm
        final float SAFETY_MARGIN = 8;
        width += (2 * SAFETY_MARGIN);
        height += (2 * SAFETY_MARGIN);
        if (width <= pageSize.widthMM && height <= pageSize.heightMM)
            return PageOrientation.PORTRAIT;
        // ..and then in landscape
        if (width <= pageSize.heightMM && height <= pageSize.widthMM)
            return PageOrientation.LANDSCAPE;
        return PageOrientation.NONE;
    }


}
