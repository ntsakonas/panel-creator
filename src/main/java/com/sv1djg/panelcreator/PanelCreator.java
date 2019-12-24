package com.sv1djg.panelcreator;

import java.io.File;
import java.io.IOException;

public class PanelCreator {
    public static void main(String[] args) {
        PanelTemplate template = new TemplateReader().readTemplate(new File(args[0]));
        TemplateRenderer renderer = new TemplateRenderer();
        try {
            renderer.render(template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
