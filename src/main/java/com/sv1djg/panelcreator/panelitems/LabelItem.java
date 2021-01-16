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

package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelItem extends PanelItem {
    @JsonProperty("text")
    public String labelText;
    @JsonProperty("size")
    public int textSize;
    @JsonProperty("font")
    public int font;
    @JsonProperty("bold")
    public boolean boldText;
    @JsonProperty("center")
    public boolean centerAlign;

    @Override
    public String toString() {
        return "LabelItem{" +
                "labelText='" + labelText + '\'' +
                ", textSize=" + textSize +
                ", font=" + font +
                ", boldText=" + boldText +
                ", centerAlign=" + centerAlign +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
