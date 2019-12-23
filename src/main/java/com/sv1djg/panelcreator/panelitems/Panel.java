package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Panel {
    @JsonProperty("width")
    public float width;
    @JsonProperty("height")
    public float height;
    @JsonProperty("units")
    public UnitType units;

    @Override
    public String toString() {
        return "Panel{" +
                "width=" + width +
                ", height=" + height +
                ", units=" + units +
                '}';
    }
}
