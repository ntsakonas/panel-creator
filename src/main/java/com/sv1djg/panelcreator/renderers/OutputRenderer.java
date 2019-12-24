package com.sv1djg.panelcreator.renderers;

import com.sv1djg.panelcreator.PanelTemplate;

import java.io.IOException;

public class OutputRenderer {
    public interface Operations {
        void addText(float x, float y, int fontSize, String text);

        void drawLine(float startX, float startY, float endX, float endY);
    }

    public void processTemplate(PanelTemplate template) throws IOException {
        FileRenderer renderer = FileRendererFactory.getRenderer();
        renderer.prepare();
        template.render(renderer.operations());
        renderer.save();
    }

}
