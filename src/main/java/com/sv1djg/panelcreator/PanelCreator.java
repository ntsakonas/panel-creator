package com.sv1djg.panelcreator;

import java.io.File;

public class PanelCreator {
    public static void main(String[] args) {
        PanelTemplate template = new TemplateReader().readTemplate(new File(args[0]));
        TemplateRenderer renderer = new TemplateRenderer();
        renderer.render(template);
    }
}
