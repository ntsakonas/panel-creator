package com.sv1djg.panelcreator.panelitems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LabelItem.class, name = "label"),
        @JsonSubTypes.Type(value = DialItem.class, name = "dial"),
        @JsonSubTypes.Type(value = SwitchItem.class, name = "switch"),
        @JsonSubTypes.Type(value = DrillHoleItem.class, name = "drillhole"),
        @JsonSubTypes.Type(value = LineItem.class, name = "line"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PanelItem {
    @JsonProperty("name")
    public String itemName;
    @JsonProperty("x")
    public float xPosition;
    @JsonProperty("y")
    public float yPosition;

    @Override
    public String toString() {
        return "PanelItem{" +
                "itemName='" + itemName + '\'' +
                ", xPosition='" + xPosition + '\'' +
                ", yPosition='" + yPosition + '\'' +
                '}';
    }
}
