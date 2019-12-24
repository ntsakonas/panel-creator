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
        if (circle.showDrillPoint) {
            // draw vertical line in the circle
            operations.drawLine(circle.xPosition, circle.yPosition + circle.diameter / 2.0f,
                    circle.xPosition, circle.yPosition - circle.diameter / 2.0f, 0.2f);
            // draw horizontal line in the circle
            operations.drawLine(circle.xPosition + circle.diameter / 2.0f, circle.yPosition,
                    circle.xPosition - circle.diameter / 2.0f, circle.yPosition, 0.2f);
        }
    }
}
