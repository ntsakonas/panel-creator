package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.RectangleItem;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class RectangleRenderer implements ItemRenderer {
    private final RectangleItem rectangle;

    public RectangleRenderer(RectangleItem rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Rectangle " + (rectangle.itemName != null ? rectangle.itemName : ""));
        float startX = rectangle.xPosition;
        float startY = rectangle.yPosition;
        operations.drawRectangle(startX, startY, rectangle.width, rectangle.height, rectangle.lineWidth);
    }
}
