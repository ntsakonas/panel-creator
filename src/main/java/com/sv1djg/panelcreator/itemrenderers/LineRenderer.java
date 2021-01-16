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

import com.sv1djg.panelcreator.panelitems.LineItem;
import com.sv1djg.panelcreator.panelitems.Orientation;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class LineRenderer implements ItemRenderer {
    private LineItem line;

    public LineRenderer(LineItem line) {
        this.line = line;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Line "+ (line.itemName != null ? line.itemName : ""));
        float startX = line.xPosition;
        float startY = line.yPosition;
        float endX = line.lineOrientation == Orientation.HORIZONTAL ? startX + line.lineLength : startX;
        float endY = line.lineOrientation == Orientation.HORIZONTAL ? startY : startY + line.lineLength;
        operations.drawLine(startX, startY, endX, endY, line.lineWidth);
    }
}
