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

import com.sv1djg.panelcreator.panelitems.Panel;
import com.sv1djg.panelcreator.panelitems.PanelItem;
import com.sv1djg.panelcreator.renderers.OutputRenderer;


class PanelRenderer implements ItemRenderer {
    private Panel panel;

    public PanelRenderer(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        // draw the top level panel into the output
        renderTopLevel(operations);
        // delegate rendering to the items
        for (PanelItem item : panel.panelItems) {
            ItemRenderer itemRenderer = ItemRendererFactory.getRendererFor(item);
            itemRenderer.renderInto(operations);
        }
    }

    private void renderTopLevel(OutputRenderer.Operations operations) {
        if (panel.hasBorder) {
            float bottomLeftX = 0;
            float bottomLeftY = 0;
            float topRightX = panel.width;
            float topRightY = panel.height;

            if (panel.borderPadding > 0) {
                bottomLeftX += panel.borderPadding;
                bottomLeftY += panel.borderPadding;
                topRightX -= panel.borderPadding;
                topRightY -= panel.borderPadding;
            }

            float lineWidth = panel.borderLineWidth;
            operations.drawLine(bottomLeftX, bottomLeftY, bottomLeftX, topRightY, lineWidth);
            operations.drawLine(bottomLeftX, topRightY, topRightX, topRightY, lineWidth);
            operations.drawLine(topRightX, topRightY, topRightX, bottomLeftY, lineWidth);
            operations.drawLine(topRightX, bottomLeftY, bottomLeftX, bottomLeftY, lineWidth);
        }
    }


}
