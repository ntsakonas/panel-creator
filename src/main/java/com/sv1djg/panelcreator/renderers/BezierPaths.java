package com.sv1djg.panelcreator.renderers;

import java.util.ArrayList;
import java.util.List;

public class BezierPaths {
    public static class BezierControlPoints {
        final float x1, y1;
        final float x2, y2;
        final float x3, y3;
        final float x4, y4;

        public BezierControlPoints(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.x3 = x3;
            this.y3 = y3;
            this.x4 = x4;
            this.y4 = y4;
        }
    }


    /**
     * create an approximation of an arc using Bezier curves.
     * the angle is measured CCW with 0 degrees set at 3 o'clock (EAST)
     *
     * @param centerX    the X coordinate of the arc center (unitless)
     * @param centerY    the Y coordinate of the arc center (unitless)
     * @param radius     the radius of the arc (unitless)
     * @param startAngle the starting angle of the arc (relative to 0-degrees)
     * @param endAngle   the ending angle of the arc (relative to 0-degrees)
     * @return a list of Bezier control points that make up the arc
     */

    public static List<BezierControlPoints> createArc(float centerX, float centerY, float radius, float startAngle, float endAngle) {
        float arcExtend = endAngle - startAngle;
        if (arcExtend >= 360.0f) {
            throw new IllegalArgumentException("The requested arc is not in the range [0..360]");
        }
        // the method that creates the arcs can handle up to 90 degrees or arc
        // the requested arc must be broken down to smaller arcs <pi/2.
        // 80 degrees is chosen
        float MAX_ARC_LEN = 80.0f;
        if (arcExtend < MAX_ARC_LEN) {
            return new ArrayList<BezierControlPoints>() {{
                add(createArcPathData(centerX, centerY, radius, (float) Math.toRadians(startAngle), (float) Math.toRadians(endAngle)));
            }};
        } else {
            int numberOfSegments = (int) (arcExtend / MAX_ARC_LEN);
            int remaining = (int) (arcExtend % MAX_ARC_LEN);

            List<BezierControlPoints> controlPoints = new ArrayList<>();
            for (int segment = 0; segment < numberOfSegments; segment++) {
                controlPoints.add(createArcPathData(centerX, centerY, radius,
                        (float) Math.toRadians(startAngle + MAX_ARC_LEN * segment),
                        (float) Math.toRadians(startAngle + MAX_ARC_LEN * (segment + 1))));
            }
            if (remaining > 0.0f) {
                controlPoints.add(createArcPathData(centerX, centerY, radius,
                        (float) Math.toRadians(startAngle + MAX_ARC_LEN * numberOfSegments),
                        (float) Math.toRadians(startAngle + MAX_ARC_LEN * numberOfSegments + remaining)));
            }

            return controlPoints;
        }
    }

    /**
     * Cubic bezier approximation of a circular arc centered at the origin,
     * from (radians) a1 to a2, where a2-a1 < pi/2.  The arc's radius is r.
     * <p>
     * Returns an object with four points, where x1,y1 and x4,y4 are the arc's end points
     * and x2,y2 and x3,y3 are the cubic bezier's control points.
     * <p>
     * This algorithm is based on the approach described in:
     * A. Riškus, "Approximation of a Cubic Bezier Curve by Circular Arcs and Vice Versa,"
     * Information Technology and Control, 35(4), 2006 pp. 371-378.
     * <p>
     * Reference: https://hansmuller-flex.blogspot.com/2011/10/more-about-approximating-circular-arcs.html
     * Direct porting of method "createArcPathData" from the provided "PathArcUtils" source code
     * https://sites.google.com/site/hansmuller/flex-blog/PathArcUtils.as
     */
    private static BezierControlPoints createArcPathData(float centerX, float centerY, float radius, float a1, float a2) {
        // Compute all four points for an arc that subtends the same total angle
        // but is centered on the X-axis
        float a = (a2 - a1) / 2.0f;
        float x4 = (float) (radius * Math.cos(a));
        float y4 = (float) (radius * Math.sin(a));
        float x1 = x4;
        float y1 = -y4;
        float q1 = x1 * x1 + y1 * y1;

        float q2 = q1 + x1 * x4 + y1 * y4;
        float k2 = (float) (4.0f / 3.0f * (Math.sqrt(2 * q1 * q2) - q2) / (x1 * y4 - y1 * x4));
        float x2 = x1 - k2 * y1;
        float y2 = y1 + k2 * x1;
        float x3 = x2;
        float y3 = -y2;

        // Find the arc points' actual locations by computing x1,y1 and x4,y4
        // and rotating the control points by a + a1
        float ar = a + a1;
        float cos_ar = (float) (Math.cos(ar));
        float sin_ar = (float) (Math.sin(ar));

        return new BezierControlPoints(
                (float) (centerX + radius * Math.cos(a1)), (float) (centerY + radius * Math.sin(a1)),
                centerX + (x2 * cos_ar - y2 * sin_ar), centerY + (x2 * sin_ar + y2 * cos_ar),
                centerX + (x3 * cos_ar - y3 * sin_ar), centerY + (x3 * sin_ar + y3 * cos_ar),
                (float) (centerX + radius * Math.cos(a2)), (float) (centerY + radius * Math.sin(a2))
        );
    }

    public static List<BezierControlPoints> createCircle(float centerX, float centerY, float radius) {
        // approximate circle using bezier curves
        // http://spencermortensen.com/articles/bezier-circle/
        // using the final solution
        // P_0 = (0,1), P_1 = (c,1), P_2 = (1,c), P_3 = (1,0)
        // P_0 = (1,0), P_1 = (1,-c), P_2 = (c,-1), P_3 = (0,-1)
        // P_0 = (0,-1), P_1 = (-c,-1), P_2 = (-1,-c), P_3 = (-1,0)
        // P_0 = (-1,0), P_1 = (-1,c), P_2 = (-c,1), P_3 = (0,1)
        // with c = 0.551915024494
        final float k = 0.551915024494f;
        final float arcPoint = k * radius;

        List<BezierControlPoints> circleAsBezier = new ArrayList<>();
        circleAsBezier.add(new BezierControlPoints(centerX, centerY + radius,
                centerX + arcPoint, centerY + radius,
                centerX + radius, centerY + arcPoint,
                centerX + radius, centerY));

        circleAsBezier.add(new BezierControlPoints(centerX + radius, centerY,
                centerX + radius, centerY - arcPoint,
                centerX + arcPoint, centerY - radius,
                centerX, centerY - radius));

        circleAsBezier.add(new BezierControlPoints(centerX, centerY - radius,
                centerX - arcPoint, centerY - radius,
                centerX - radius, centerY - arcPoint,
                centerX - radius, centerY));

        circleAsBezier.add(new BezierControlPoints(centerX - radius, centerY,
                centerX - radius, centerY + arcPoint,
                centerX - arcPoint, centerY + radius,
                centerX, centerY + radius));
        return circleAsBezier;
    }
}
