package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.OutputRenderer;
import com.sv1djg.panelcreator.panelitems.SwitchItem;

public class SwitchRenderer implements ItemRenderer {
    private SwitchItem switchItem;

    public SwitchRenderer(SwitchItem switchItem) {
        this.switchItem = switchItem;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Switch");
    }
}
