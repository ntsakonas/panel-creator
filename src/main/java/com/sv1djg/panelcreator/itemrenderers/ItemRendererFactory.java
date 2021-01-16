/*
    PanelCreator - A program to quickly generate front panels and drilling templates for your DIY projects.
    Copyright (C) 2019-2021, Nick Tsakonas (SV1DJG)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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

        if (item.getClass() == CircleItem.class)
            return new CircleItemRenderer(CircleItem.class.cast(item));

        if (item.getClass() == LineItem.class)
            return new LineRenderer(LineItem.class.cast(item));

        if (item.getClass() == RectangleItem.class)
            return new RectangleRenderer(RectangleItem.class.cast(item));

        throw new IllegalArgumentException("Unknown type " + item.getClass().getName());
    }
}