package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem extends PanelItem {
    @JsonProperty("orientation")
    public Orientation lineOrientation;
    @JsonProperty("length")
    public float lineLength;

    @Override
    public String toString() {
        return "LineItem{" +
                "lineOrientation=" + lineOrientation +
                ", lineLength=" + lineLength +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
