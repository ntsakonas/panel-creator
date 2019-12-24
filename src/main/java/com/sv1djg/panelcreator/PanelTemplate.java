package com.sv1djg.panelcreator;

import com.sv1djg.panelcreator.itemrenderers.ItemRenderer;
import com.sv1djg.panelcreator.itemrenderers.ItemRendererFactory;
import com.sv1djg.panelcreator.panelitems.Panel;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class PanelTemplate {
    private Panel panel;

    public PanelTemplate(Panel panel) {
        this.panel = panel;
    }

    public void render(OutputRenderer.Operations outputRenderer) {
        ItemRenderer renderer = ItemRendererFactory.getRendererFor(panel);
        renderer.renderInto(outputRenderer);
    }
}