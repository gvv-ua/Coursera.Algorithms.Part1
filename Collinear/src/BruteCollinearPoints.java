import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private Point[] points;
    private int count = 0;
    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
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

        List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < this.points.length; i++) {
            for (int j = i + 1; j < this.points.length; j++) {
                for (int k = j + 1; k < this.points.length; k++) {
                    for (int l = k + 1; l < this.points.length; l++) {
                        double slope1 = this.points[i].slopeTo(this.points[j]);
                        double slope2 = this.points[i].slopeTo(this.points[k]);
                        double slope3 = this.points[i].slopeTo(this.points[l]);
//                        if (slope1 == slope2 && slope1 == slope3) {
                        if ((Double.compare(slope1, slope2) == 0) && (Double.compare(slope1, slope3) == 0)) {
                            Point[] line = new Point[4];
                            line[0] = this.points[i];
                            line[1] = this.points[j];
                            line[2] = this.points[k];
                            line[3] = this.points[l];
                            Arrays.sort(line);
                            lineSegments.add(new LineSegment(line[0], line[3]));
                            count++;
                        }
                    }
                }

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
     * the line segments
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
