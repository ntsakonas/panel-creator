package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.OutputRenderer;
import com.sv1djg.panelcreator.panelitems.LabelItem;

public class LabelRenderer implements ItemRenderer {
    private LabelItem label;

    public LabelRenderer(LabelItem label) {
        this.label = label;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Label");
        operations.addText(label.xPosition,label.yPosition,label.textSize,label.labelText);
    }
}
