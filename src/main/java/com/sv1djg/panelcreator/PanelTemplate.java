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

package com.sv1djg.panelcreator;

import com.sv1djg.panelcreator.itemrenderers.ItemRenderer;
import com.sv1djg.panelcreator.itemrenderers.ItemRendererFactory;
import com.sv1djg.panelcreator.panelitems.Panel;
import com.sv1djg.panelcreator.panelitems.UnitType;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

public class PanelTemplate {
    private Panel panel;

    public PanelTemplate(Panel panel) {
        this.panel = panel;
    }

    public UnitType getUnits() {
        return panel.units != null ? panel.units : UnitType.MM;
    }

    public float getWidth() {
        return panel.width;
    }

    public float getHeight() {
        return panel.height;
    }

    public void render(OutputRenderer.Operations outputRenderer) {
        ItemRenderer renderer = ItemRendererFactory.getRendererFor(panel);
        renderer.renderInto(outputRenderer);
    }
}
