package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DrillHoleItem extends PanelItem {
    @JsonProperty("holedia")
    public float holeDiameter;

    @Override
    public String toString() {
        return "DrillHoleItem{" +
                "holeDiameter=" + holeDiameter +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
