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

            float lineWidth = 0.2f;
            operations.drawLine(bottomLeftX * 10.0f, bottomLeftY * 10.0f, bottomLeftX * 10.0f, topRightY * 10.0f, lineWidth);
            operations.drawLine(bottomLeftX * 10.0f, topRightY * 10.0f, topRightX * 10.0f, topRightY * 10.0f, lineWidth);
            operations.drawLine(topRightX * 10.0f, topRightY * 10.0f, topRightX * 10.0f, bottomLeftY * 10.0f, lineWidth);
            operations.drawLine(topRightX * 10.0f, bottomLeftY * 10.0f, bottomLeftX * 10.0f, bottomLeftY * 10.0f, lineWidth);

            // operations.drawRectangle(bottomLeftX *10.f, bottomLeftY*10.f, (topRightX - bottomLeftX)*10.f, (topRightY - bottomLeftY)*10.f, 1.0f);
        }
    }


}
