package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwitchItem extends PanelItem {
    @JsonProperty("holedia")
    public float holeDiameter;
    @JsonProperty("positions")
    public int positions;
    @JsonProperty("text_1")
    public String text1;
    @JsonProperty("text_2")
    public String text2;
    @JsonProperty("text_3")
    public String text3;
    @JsonProperty("orientation")
    public Orientation switchOrientation;

    @Override
    public String toString() {
        return "SwitchItem{" +
                "holeDiameter=" + holeDiameter +
                ", positions=" + positions +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                ", switchOrientation=" + switchOrientation +
                ", itemName='" + itemName + '\'' +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
