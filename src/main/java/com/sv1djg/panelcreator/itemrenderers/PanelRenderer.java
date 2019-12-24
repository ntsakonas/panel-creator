package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.renderers.OutputRenderer;
import com.sv1djg.panelcreator.panelitems.Panel;
import com.sv1djg.panelcreator.panelitems.PanelItem;


class PanelRenderer implements ItemRenderer {
    private Panel panel;

    public PanelRenderer(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        // draw the top level panel into the pdf
        if (panel.hasBorder) {

        }
        // delegate rendering to the items
        for (PanelItem item : panel.panelItems) {
            ItemRenderer itemRenderer = ItemRendererFactory.getRendererFor(item);
            itemRenderer.renderInto(operations);
        }
    }


}
