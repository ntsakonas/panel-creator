package com.sv1djg.panelcreator;

import com.sv1djg.panelcreator.itemrenderers.ItemRenderer;
import com.sv1djg.panelcreator.itemrenderers.RendererFactory;
import com.sv1djg.panelcreator.panelitems.Panel;

public class PanelTemplate {
    private Panel panel;

    public PanelTemplate(Panel panel) {
        this.panel = panel;
    }

    public void render(OutputRenderer.Operations outputRenderer) {
        ItemRenderer renderer = RendererFactory.getRendererFor(panel);
        renderer.renderInto(outputRenderer);
    }
}
