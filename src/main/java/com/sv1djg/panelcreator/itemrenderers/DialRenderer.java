package com.sv1djg.panelcreator.itemrenderers;

import com.sv1djg.panelcreator.panelitems.DialItem;
import com.sv1djg.panelcreator.panelitems.ScaleTicks;
import com.sv1djg.panelcreator.renderers.OutputRenderer;

import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;

public class DialRenderer implements ItemRenderer {
    private DialItem dial;

    public DialRenderer(DialItem dial) {
        this.dial = dial;
    }

    @Override
    public void renderInto(OutputRenderer.Operations operations) {
        System.out.println("Rendering Dial " + (dial.itemName != null ? dial.itemName : ""));
        // the dial.anchor is the angle with reference 0 degrees at 12'0clock (NORTH) where the
        // middle of the unoccupied circle segment will rest
        float unoccupiedSegment = 360.0f - dial.extendAngle;
        // this is the anchor angle in the drawing system (0 degrees at EAST)
        float anchorAngle = (360.0f - dial.anchorAngle + 90.0f) % 360.0f;
        // split the unoccupied segment around the anchor (i.e add half of it to the anchor angle)
        float startAngle = anchorAngle + unoccupiedSegment / 2.0f;
        float endAngle = startAngle + dial.extendAngle;
        if (dial.showBaseLine) {
            operations.drawArc(dial.xPosition, dial.yPosition, dial.dialDiameter / 2.0f, startAngle, endAngle, dial.lineWidth);
        }
        // continue with the dial scale...
        if (dial.dialScale != null) {
            renderDialScale(operations, startAngle, endAngle);
        }
    }

    private void renderDialScale(OutputRenderer.Operations operations, float startAngle, float endAngle) {
        // the dial scale comes in 2 variants:
        // - the one that is made of a min-max with equal divisions (and subdivisions)
        // - the one that is made of custom marks placed at specific angles
        // in both cases, processing the description we will end up with 2 lists
        // (a) angles and (b) texts whose elements match pairwise
        // these 2 list are used to draw the ticks and texts

        if (dial.dialScale.scaleTicks == null || dial.dialScale.scaleTicks.isEmpty())
            return;

        dial.dialScale.scaleTicks.stream()
                .map(tick -> generateTicks(tick, startAngle, endAngle))
                .forEach(ticks -> drawTicks(ticks, operations));

    }

    private void drawTicks(ScaleTicksDescriptor ticks, OutputRenderer.Operations operations) {
        // iterate on the ticks and generate small lines and also calculate the location of text in the extension
        // of the tick line
        float centerX = dial.xPosition;
        float centerY = dial.yPosition;
        float dialRadius = dial.dialDiameter / 2.0f;
        // in case this is not defined,place it at 3 ticks length
        float labelRingRadius = ticks.labelRingRadius > 0
                ? ticks.labelRingRadius
                : dialRadius + 3 * ticks.tickLength;

        ticks.tickMarks.stream().forEach(tickmark -> {
            float tickAngle = tickmark.getKey().floatValue();
            String tickLabel = tickmark.getValue();

            float tickDirectionX = (float) (Math.cos(Math.toRadians(tickAngle)));
            float tickStartX = centerX + dialRadius * tickDirectionX;
            float tickEndX = centerX + (dialRadius + ticks.tickLength) * tickDirectionX;
            float tickLabelX = centerX + labelRingRadius * tickDirectionX;

            float tickDirectionY = (float) (Math.sin(Math.toRadians(tickAngle)));
            float tickStartY = centerY + dialRadius * tickDirectionY;
            float tickEndY = centerY + (dialRadius + ticks.tickLength) * tickDirectionY;
            float tickLabelY = centerY + labelRingRadius * tickDirectionY;

            operations.drawLine(tickStartX, tickStartY, tickEndX, tickEndY, ticks.tickWidth);
            if (tickLabel != null && !tickLabel.trim().isEmpty()) {
                operations.addText(tickLabelX, tickLabelY, tickLabel, ticks.tickLabelFont, ticks.tickLabelSize, ticks.tickLabelBold, true);
            }
        });
    }

    private class ScaleTicksDescriptor {
        List<SimpleImmutableEntry<Float, String>> tickMarks;
        float tickLength;
        float tickWidth;
        int tickLabelFont;
        int tickLabelSize;
        boolean tickLabelBold;
        float labelRingRadius;

        public ScaleTicksDescriptor(List<SimpleImmutableEntry<Float, String>> tickMarks, float tickLength, float tickWidth, int tickLabelFont, int tickLabelSize, boolean tickLabelBold, float labelRingRadius) {
            this.tickMarks = tickMarks;
            this.tickLength = tickLength;
            this.tickWidth = tickWidth;
            this.tickLabelFont = tickLabelFont;
            this.tickLabelSize = tickLabelSize;
            this.tickLabelBold = tickLabelBold;
            this.labelRingRadius = labelRingRadius;
        }
    }

    private ScaleTicksDescriptor generateTicks(ScaleTicks scaleTicks, float startAngle, float endAngle) {
        // check which variant we have
        if (hasCustomScale(scaleTicks)) {
            // if the angles and texts are not of the same length, then the minimum number of pairs is used
            // and a warning is printed
            int numberOfTicks = scaleTicks.customValuesAnchorAngles.size();
            int numberOfLabels = scaleTicks.customValuesTexts != null ? scaleTicks.customValuesTexts.size() : 0;
            boolean showTickValues = scaleTicks.showValues && numberOfLabels > 0;
            if (showTickValues && numberOfLabels > 0 && numberOfTicks != numberOfLabels) {
                numberOfTicks = Math.min(numberOfTicks, numberOfLabels);
                System.out.println("WARNING: scale for dial " + (dial.itemName != null ? dial.itemName : "") + " has unequal number of angles/texts.");
            }

            List<SimpleImmutableEntry<Float, String>> tickMarks = new ArrayList<>();

            for (int tickNumber = 0; tickNumber < numberOfTicks; tickNumber++) {
                float tickAngle = endAngle - scaleTicks.customValuesAnchorAngles.get(tickNumber);
                String tickText = showTickValues ?
                        scaleTicks.customValuesTexts.get(tickNumber)
                        : "";
                tickMarks.add(new SimpleImmutableEntry<>(tickAngle, tickText));
            }

            return new ScaleTicksDescriptor(tickMarks, scaleTicks.tickLength, scaleTicks.tickWidth, scaleTicks.font, scaleTicks.textSize, scaleTicks.boldText, scaleTicks.labelRingDiameter / 2.0f);
        } else {
            // divide the scale range into equal arc distances and prepare the text to go with each
            // tick (if required)
            float scaleRange = dial.dialScale.maxValue - dial.dialScale.minValue;
            int numberOfTicks = (int) (scaleRange / scaleTicks.tickStep);
            float tickIncrementAngle = (endAngle - startAngle) / numberOfTicks;

            DecimalFormat tickLabelFormatter = new DecimalFormat();
            tickLabelFormatter.setMaximumFractionDigits(scaleTicks.tickValueDecimals);
            List<SimpleImmutableEntry<Float, String>> tickMarks = new ArrayList<>();

            // after drawing is done, check if this should be <=
            for (int tickNumber = 0; tickNumber <= numberOfTicks; tickNumber++) {
                float tickAngle = endAngle - tickIncrementAngle * tickNumber;
                String tickText = scaleTicks.showValues ?
                        tickLabelFormatter.format(dial.dialScale.minValue + scaleTicks.tickStep * tickNumber)
                        : "";
                tickMarks.add(new SimpleImmutableEntry<>(tickAngle, tickText));
            }

            return new ScaleTicksDescriptor(tickMarks, scaleTicks.tickLength, scaleTicks.tickWidth, scaleTicks.font, scaleTicks.textSize, scaleTicks.boldText, scaleTicks.labelRingDiameter / 2.0f);
        }
    }

    private boolean hasCustomScale(ScaleTicks scaleTicks) {
        return scaleTicks.customValuesAnchorAngles != null && !scaleTicks.customValuesAnchorAngles.isEmpty();
    }
}
