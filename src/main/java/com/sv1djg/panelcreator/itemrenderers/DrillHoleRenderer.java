package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.RenderOutput;
import com.sv1djg.panelcreator.panelitems.DrillHoleItem;

public class DrillHoleRenderer implements ItemRenderer {
    private DrillHoleItem hole;

    public DrillHoleRenderer(DrillHoleItem hole) {
        this.hole = hole;
    }

    @Override
    public void renderInto(RenderOutput pdf) {
        System.out.println("Rendering Hole");
    }
}
