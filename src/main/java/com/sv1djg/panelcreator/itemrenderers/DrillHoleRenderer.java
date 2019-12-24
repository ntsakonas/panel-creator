package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.renderers.OutputRenderer;
import com.sv1djg.panelcreator.panelitems.DrillHoleItem;

public class DrillHoleRenderer implements ItemRenderer {
    private DrillHoleItem hole;

    public DrillHoleRenderer(DrillHoleItem hole) {
        this.hole = hole;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Hole");
    }
}
