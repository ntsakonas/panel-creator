package com.sv1djg.panelcreator;

import java.io.IOException;

public class TemplateRenderer {
    public void render(PanelTemplate template) throws IOException {
        OutputRenderer outputRenderer = new OutputRenderer();
        outputRenderer.processTemplate(template);
    }
}
