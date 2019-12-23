package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleTicks {
    @JsonProperty("values")
    public boolean showValues;
    @JsonProperty("length")
    public float tickLength;
    @JsonProperty("width")
    public float tickWidth;
    @JsonProperty("step")
    public float tickStep;
    @JsonProperty("resolution")
    public float tickValueResolution;

    @Override
    public String toString() {
        return "ScaleTicks{" +
                "showValues=" + showValues +
                ", tickLength=" + tickLength +
                ", tickWidth=" + tickWidth +
                ", tickStep=" + tickStep +
                ", tickValueResolution=" + tickValueResolution +
                '}';
    }
}
