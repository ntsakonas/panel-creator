package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
    @JsonProperty("decimals")
    public int tickValueDecimals;
    @JsonProperty("angles")
    public List<Float> customValuesAnchorAngles;
    @JsonProperty("text")
    public List<String> customValuesTexts;
    @JsonProperty("size")
    public int textSize;
    @JsonProperty("font")
    public int font;
    @JsonProperty("bold")
    public boolean boldText;
    @JsonProperty("labeldia")
    public float labelRingDiameter;

    @Override
    public String toString() {
        return "ScaleTicks{" +
                "showValues=" + showValues +
                ", tickLength=" + tickLength +
                ", tickWidth=" + tickWidth +
                ", tickStep=" + tickStep +
                ", tickValueDecimals=" + tickValueDecimals +
                ", customValuesAnchorAngles=" + customValuesAnchorAngles +
                ", customValuesTexts=" + customValuesTexts +
                ", textSize=" + textSize +
                ", font=" + font +
                ", boldText=" + boldText +
                ", labelRingDiameter=" + labelRingDiameter +
                '}';
    }
}
