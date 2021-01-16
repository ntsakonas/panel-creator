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

package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.CircleItem;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class CircleItemRenderer implements ItemRenderer {
    private CircleItem circle;

    public CircleItemRenderer(CircleItem circle) {
        this.circle = circle;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Circle " + (circle.itemName != null ? circle.itemName : ""));
        operations.drawCircle(circle.xPosition, circle.yPosition, circle.diameter, circle.lineWidth);
        if (circle.showDrillMark) {
            // draw vertical line in the circle
            operations.drawLine(circle.xPosition, circle.yPosition + circle.diameter / 2.0f,
                    circle.xPosition, circle.yPosition - circle.diameter / 2.0f, 0.2f);
            // draw horizontal line in the circle
            operations.drawLine(circle.xPosition + circle.diameter / 2.0f, circle.yPosition,
                    circle.xPosition - circle.diameter / 2.0f, circle.yPosition, 0.2f);
        }
    }
}
