package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialItem extends PanelItem {
    @JsonProperty("dia")
    public float dialDiameter;
    @JsonProperty("extend")
    public float extendAngle;
    @JsonProperty("anchor")
    public float anchorAngle;
    @JsonProperty("baseline")
    public boolean showBaseLine;
    @JsonProperty("line")
    public float lineWidth;
    @JsonProperty("scale")
    public DialScale dialScale;


    @Override
    public String toString() {
        return "DialItem{" +
                "dialDiameter=" + dialDiameter +
                ", extendAngle=" + extendAngle +
                ", anchorAngle=" + anchorAngle +
                ", showBaseLine=" + showBaseLine +
                ", dialScale=" + dialScale +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
