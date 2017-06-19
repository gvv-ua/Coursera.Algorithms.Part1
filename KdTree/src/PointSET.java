import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;

    /**
     * Construct an empty set of points
     */
    public PointSET() {
        set = new SET<>();
    }

    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (!contains(p)) {
            set.add(p);
        }
    }

    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        for (Point2D point: set) {
            point.draw();
        }
    }

    /**
     * All points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> rangeSet = new SET<>();
        for (Point2D point: set) {
            if (rect.contains(point)) {
                rangeSet.add(point);
            }
        }
        return  rangeSet;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) {
            return null;
        } else {
            double min = -1;
            double distqnce;
            Point2D minPoint = null;

            for (Point2D point : set) {
                distqnce = point.distanceSquaredTo(p);
                if ((min < 0) || (distqnce < min)) {
                    minPoint = point;
                    min = distqnce;
                }
            }
            return minPoint;
        }
    }

    public static void main(String[] args) {
        // write your code here
    }
}
