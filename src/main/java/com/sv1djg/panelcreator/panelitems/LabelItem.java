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
