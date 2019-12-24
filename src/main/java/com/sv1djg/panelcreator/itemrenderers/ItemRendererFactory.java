package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.*;

public class ItemRendererFactory {
    public static ItemRenderer getRendererFor(Panel panel) {
        return new PanelRenderer(panel);
    }


    public static ItemRenderer getRendererFor(PanelItem item) {
        if (item.getClass() == LabelItem.class)
            return new LabelRenderer(LabelItem.class.cast(item));

        if (item.getClass() == DialItem.class)
            return new DialRenderer(DialItem.class.cast(item));

        if (item.getClass() == SwitchItem.class)
            return new SwitchRenderer(SwitchItem.class.cast(item));

        if (item.getClass() == DrillHoleItem.class)
            return new DrillHoleRenderer(DrillHoleItem.class.cast(item));

        if (item.getClass() == LineItem.class)
            return new LineRenderer(LineItem.class.cast(item));

        throw new IllegalArgumentException("Unknown type " + item.getClass().getName());
    }
}