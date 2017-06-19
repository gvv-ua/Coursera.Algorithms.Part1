import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

/**
 * Created by gvv on 16.06.17.
 */
public class KdTree {
    private Node root;
    private int size = 0;


    public KdTree() {
        root = null;
    }

    /**
     * Is the root empty?
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Number of points in the root
     */
    public int size() {
        return size;
    }

    /**
     * Add the point to the root (if it is not already in the root)
     */
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (!contains(p)) {
            root = insert(root, p, true, 0, 0, 1, 1);
        }
    }

    private Node insert(Node node, Point2D p, boolean vertical, double x1, double y1, double x2, double y2) {
        if (node == null) {
            RectHV rect = new RectHV(x1, y1, x2, y2);
            size++;
            return new Node(p, rect);
        }
        if (vertical) {
            double nodex = node.point.x();
            if (p.x() < nodex) {
                node.lb = insert(node.lb, p, !vertical, x1, y1, nodex, y2);
            } else {
                node.rt = insert(node.rt, p, !vertical, nodex, y1, x2, y2);
            }
        } else {
            double nodey = node.point.y();
            if (p.y() < nodey) {
                node.lb = insert(node.lb, p, !vertical, x1, y1, x2, nodey);
            } else {
                node.rt = insert(node.rt, p, !vertical, x1, nodey, x2, y2);
            }
        }
        return node;
    }

    /**
     * Does the root contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, true) != null;
    }

    private Node contains(Node node, Point2D p, boolean vertical) {
        if (node == null) return null;
        double nodex = node.point.x();
        double nodey = node.point.y();
        double px = p.x();
        double py = p.y();
        if (Double.compare(nodex, px) == 0 && Double.compare(nodey, py) == 0) {
            return node;
        }
        if (vertical) {
            return (px < nodex) ? contains(node.lb, p, !vertical) : contains(node.rt, p, !vertical);
        } else {
            return (py < nodey) ? contains(node.lb, p, !vertical) : contains(node.rt, p, !vertical);
        }
    }


    /**
     * Draw all points to standard draw
     */
    public void draw() {
        StdDraw.clear();
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();

            StdDraw.setPenRadius(0.005);
            if (vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            }
            draw(node.rt, !vertical);
            draw(node.lb, !vertical);
        }
    }

    /**
     * All points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();

        range(root, rect, queue);
        return  queue;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null) return;
        if (rect.contains(node.point)) {
            queue.enqueue(node.point);
        }

        if (rect.intersects(node.rect)) {
            range(node.lb, rect, queue);
            range(node.rt, rect, queue);
        }
    }


    /**
     * A nearest neighbor in the root to point p; null if the root is empty
     */
    public Point2D nearest(Point2D p) {
        if (root == null) return  null;
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D p, Point2D closest) {
        if (node == null) return  closest;
        double distance = node.point.distanceSquaredTo(p);
        double closestDistance = closest.distanceSquaredTo(p);

        if (distance < closestDistance) {
            closest = node.point;
            closestDistance = closest.distanceSquaredTo(p);
        }

        double distanceLeft = -1;
        if (node.lb != null) {
            distanceLeft = node.lb.rect.distanceSquaredTo(p);
        }
        boolean isLeft = (distanceLeft >= 0 && distanceLeft < closestDistance);
        double distanceRight = -1;
        if (node.rt != null) {
            distanceRight = node.rt.rect.distanceSquaredTo(p);
        }
        boolean isRight = (distanceRight >= 0 && distanceRight < closestDistance);
        if (isLeft && isRight) {
            if (distanceLeft < distanceRight) {
                closest = nearest(node.lb, p, closest);
                closest = nearest(node.rt, p, closest);
            } else {
                closest = nearest(node.rt, p, closest);
                closest = nearest(node.lb, p, closest);
            }
        } else if (isLeft) {
            closest = nearest(node.lb, p, closest);
        } else if (isRight) {
            closest = nearest(node.rt, p, closest);
        }
        return closest;
    }


    public static void main(String[] args) {
        // write your code here
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

//        Point2D p = new Point2D(0.81, 0.3);
//        kdtree.insert(p);

        kdtree.draw();
        StdDraw.show();
        System.out.println("size=" + kdtree.size());
        Point2D nearest = kdtree.nearest(new Point2D(0.81, 0.3));
        System.out.println("nearest=" + nearest);

    }

    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.point = p;
        }

        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
    }
}
