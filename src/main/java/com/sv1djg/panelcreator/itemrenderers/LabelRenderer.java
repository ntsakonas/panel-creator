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
        // X,Y must be provided as mm in the operation
        // the template has the values in cm
        operations.addText(label.xPosition * 10.0f, label.yPosition * 10.0f, label.textSize, label.labelText);
    }
}
