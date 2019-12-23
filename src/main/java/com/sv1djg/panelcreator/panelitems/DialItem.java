package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialItem extends PanelItem {
    @JsonProperty("dia")
    public float dialDiameter;

    @Override
    public String toString() {
        return "DialItem{" +
                "dialDiameter=" + dialDiameter +
                '}';
    }
}
