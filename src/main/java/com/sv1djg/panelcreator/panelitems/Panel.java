package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Panel {
    @JsonProperty("width")
    public float width;
    @JsonProperty("height")
    public float height;
    @JsonProperty("units")
    public UnitType units;
    @JsonProperty("border")
    public boolean hasBorder;
    @JsonProperty("padding")
    public float borderPadding;
    @JsonProperty("items")
    public List<PanelItem> panelItems;

    @Override
    public String toString() {
        return "Panel{" +
                "width=" + width +
                ", height=" + height +
                ", units=" + units +
                ", hasBorder=" + hasBorder +
                ", borderPadding=" + borderPadding +
                ", panelItems=" + panelItems +
                '}';
    }
}
