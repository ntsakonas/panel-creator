package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.LineItem;
import com.sv1djg.panelcreator.panelitems.Orientation;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class LineRenderer implements ItemRenderer {
    private LineItem line;

    public LineRenderer(LineItem line) {
        this.line = line;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Line");
        float startX = line.xPosition;
        float startY = line.yPosition;
        float endX = line.lineOrientation == Orientation.HORIZONTAL ? startX + line.lineLength : startX;
        float endY = line.lineOrientation == Orientation.HORIZONTAL ? startY : startY + line.lineLength;
        operations.drawLine(startX, startY, endX, endY, line.lineWidth);
    }
}
