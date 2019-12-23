package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.RenderOutput;
import com.sv1djg.panelcreator.panelitems.DialItem;

public class DialRenderer implements ItemRenderer {
    private DialItem dial;

    public DialRenderer(DialItem dial) {
        this.dial = dial;
    }

    @Override
    public void renderInto(RenderOutput pdf) {
        System.out.println("Rendering Dial");
    }
}
