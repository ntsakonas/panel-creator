package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.CircleItem;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class CircleItemRenderer implements ItemRenderer {
    private CircleItem circle;

    public CircleItemRenderer(CircleItem circle) {
        this.circle = circle;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Circle");
        operations.drawCircle(circle.xPosition, circle.yPosition, circle.diameter, circle.lineWidth);
    }
}
