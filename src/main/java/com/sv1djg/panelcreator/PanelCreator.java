package com.sv1djg.panelcreator;

import java.io.File;

public class PanelCreator {
    public static void main(String[] args) {
        TemplateReader rd = new TemplateReader(new File(args[0]));
        TemplateRenderer renderer = new TemplateRenderer();
        renderer.render(rd.getTemplate());
    }
}
