package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.RenderOutput;
import com.sv1djg.panelcreator.panelitems.LineItem;

public class LineRenderer implements ItemRenderer {
    private LineItem line;

    public LineRenderer(LineItem line) {
        this.line = line;
    }

    @Override
    public void renderInto(RenderOutput pdf) {
        System.out.println("Rendering Line");
    }
}
