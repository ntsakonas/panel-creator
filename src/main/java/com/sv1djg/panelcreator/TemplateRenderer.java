package com.sv1djg.panelcreator;

public class TemplateRenderer {
    public void render(PanelTemplate template) {
        // create the target pdf object that will receive the output
        // add methods in the RenderOutput object as required by the renderers
        // chose a better name
        RenderOutput renderOutput = new RenderOutput();
        template.render(renderOutput);

    }
}
