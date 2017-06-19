import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gvv on 02.06.17.
 */
public class FastCollinearPoints {
    private Point[] points;
    private int count = 0;
    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        Point prev = null;
        for (Point point : this.points) {
            if (point == null) {
                throw new NullPointerException();
            } else if (prev != null && point.compareTo(prev) == 0) {
                throw new IllegalArgumentException();
            }
            prev = point;
        }
        parseSegments();
    }

    private void parseSegments() {
        List<LineSegment> lineSegments = new ArrayList<>();


        for (int i = 0; i < points.length - 1; i++) {
            Point[] pointsBySlope = Arrays.copyOf(points, points.length);

            Point iPoint = points[i];
            Arrays.sort(pointsBySlope, iPoint.slopeOrder());

            int equals = 1;
            double prev = iPoint.slopeTo(pointsBySlope[0]);

            for (int j = 1; j < pointsBySlope.length; j++) {
                double slope = iPoint.slopeTo(pointsBySlope[j]);
                boolean isSlopeEqual = (Double.compare(slope, prev) == 0);
                if (isSlopeEqual) {
                    equals++;
                }
                if (!isSlopeEqual || j == pointsBySlope.length - 1) {
                    if (equals >= 3) {
                        Point[] line = new Point[equals + 1];
                        for (int k = 0; k < equals; k++) {
                            line[k] = (j == pointsBySlope.length - 1 && isSlopeEqual)
                                    ? pointsBySlope[j - k]
                                    : pointsBySlope[j - k - 1];
                        }
                        line[equals] = iPoint;
                        Arrays.sort(line);

                        if (line[0].compareTo(iPoint) == 0) {
                            lineSegments.add(new LineSegment(line[0], line[line.length - 1]));
                            count++;
                        }

                    }
                    equals = 1;
                }

                prev = slope;
            }
        }

        segments = new LineSegment[lineSegments.size()];
        segments = lineSegments.toArray(segments);
    }

    /**
     * The number of line segments
     */
    public int numberOfSegments() {
        return count;

    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
