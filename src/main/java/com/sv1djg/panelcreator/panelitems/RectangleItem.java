package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RectangleItem extends PanelItem {
    @JsonProperty("height")
    public float height;
    @JsonProperty("width")
    public float width;
    @JsonProperty("line")
    public float lineWidth;

    @Override
    public String toString() {
        return "RectangleItem{" +
                "height=" + height +
                ", width=" + width +
                ", lineWidth=" + lineWidth +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}

