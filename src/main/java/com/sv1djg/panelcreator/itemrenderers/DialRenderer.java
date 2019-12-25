package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.DialItem;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class DialRenderer implements ItemRenderer {
    private DialItem dial;

    public DialRenderer(DialItem dial) {
        this.dial = dial;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Dial " + (dial.itemName != null ? dial.itemName : ""));
        if (dial.showBaseLine) {
            // the dial.anchor is the angle with reference 0 degrees at 12'0clock (NORTH) where the
            // middle of the unoccupied circle segment will rest
            float unoccupiedSegment = 360.0f - dial.extendAngle;
            // this is the anchor angle in the drawing system (0 degrees at EAST)
            float anchorAngle = (360.0f - dial.anchorAngle + 90.0f) % 360.0f;
            // split the unoccupied segment around the anchor (i.e add half of it to the anchor angle)
            float startAngle = anchorAngle + unoccupiedSegment / 2.0f;
            float endAngle = startAngle + dial.extendAngle;
            operations.drawArc(dial.xPosition, dial.yPosition, dial.dialDiameter / 2.0f, startAngle, endAngle, dial.lineWidth);
        }
        // continue with the ticks...
    }
}
