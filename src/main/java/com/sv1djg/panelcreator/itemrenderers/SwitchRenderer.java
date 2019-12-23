package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.RenderOutput;
import com.sv1djg.panelcreator.panelitems.SwitchItem;

public class SwitchRenderer implements ItemRenderer {
    private SwitchItem switchItem;

    public SwitchRenderer(SwitchItem switchItem) {
        this.switchItem = switchItem;
    }

    @Override
    public void renderInto(RenderOutput pdf) {
        System.out.println("Rendering Switch");
    }
}
