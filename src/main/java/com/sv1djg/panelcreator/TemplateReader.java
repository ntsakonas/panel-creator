package com.sv1djg.panelcreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv1djg.panelcreator.panelitems.Panel;

import java.io.File;

public class TemplateReader {
    public TemplateReader(File templateFile) {
        try {

            ObjectMapper mapper = new ObjectMapper(); // create once, reuse
            Panel value = mapper.readValue(templateFile, Panel.class);
            // MyValue value = mapper.readValue("{\"myname\":\"Bob\", \"age\":13}", MyValue.class);
            System.out.println("Deserialised->"+value);

        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public Object getTemplate() {
        return null;
    }
}
