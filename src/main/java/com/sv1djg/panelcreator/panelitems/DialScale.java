package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialScale {
    @JsonProperty("min")
    public float minValue;
    @JsonProperty("max")
    public float maxValue;
    @JsonProperty("ticks")
    public List<ScaleTicks> scaleTicks;

    @Override
    public String toString() {
        return "DialScale{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", scaleTicks=" + scaleTicks +
                '}';
    }
}
