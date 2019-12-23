package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.RenderOutput;
import com.sv1djg.panelcreator.panelitems.LabelItem;

public class LabelRenderer implements ItemRenderer {
    private LabelItem label;

    public LabelRenderer(LabelItem label) {
        this.label = label;
    }

    @Override
    public void renderInto(RenderOutput pdf) {
        System.out.println("Rendering Label");
    }
}
