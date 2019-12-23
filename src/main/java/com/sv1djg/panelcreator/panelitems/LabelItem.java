package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelItem extends PanelItem{
    @JsonProperty("text")
    public String labelText;
    @JsonProperty("size")
    public int textSize;
    @JsonProperty("font")
    public int font;

    @Override
    public String toString() {
        return "LabelItem{" +
                "labelText='" + labelText + '\'' +
                ", textSize=" + textSize +
                ", font=" + font +
                '}';
    }
}
