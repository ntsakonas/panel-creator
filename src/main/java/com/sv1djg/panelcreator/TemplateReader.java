package com.sv1djg.panelcreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv1djg.panelcreator.panelitems.Panel;

import java.io.File;

public class TemplateReader {

    public PanelTemplate readTemplate(File templateFile) {
        try {
            ObjectMapper mapper = new ObjectMapper(); // create once, reuse
            Panel panel = mapper.readValue(templateFile, Panel.class);
            System.out.println("Deserialised->" + panel);
            return new PanelTemplate(panel);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IllegalStateException("No panel description found.");
        }
    }
}
