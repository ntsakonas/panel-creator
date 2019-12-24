package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CircleItem extends PanelItem {
    @JsonProperty("dia")
    public float diameter;
    @JsonProperty("line")
    public float lineWidth;
    @JsonProperty("drillpoint")
    public boolean showDrillPoint;

    @Override
    public String toString() {
        return "CircleItem{" +
                "diameter=" + diameter +
                ", lineWidth=" + lineWidth +
                ", showDrillPoint=" + showDrillPoint +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
