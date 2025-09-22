package algorithms;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static double closestPair(Point[] points) {
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints, Comparator.comparingDouble(Point::getX));  // Using Comparator
        return closestPairRec(sortedPoints);
    }

    private static double closestPairRec(Point[] points) {
        int n = points.length;
        if (n <= 3) return bruteForce(points);
        int mid = n / 2;
        Point midPoint = points[mid];
        double leftDist = closestPairRec(Arrays.copyOfRange(points, 0, mid));
        double rightDist = closestPairRec(Arrays.copyOfRange(points, mid, n));
        double minDist = Math.min(leftDist, rightDist);
        return Math.min(minDist, stripClosest(points, midPoint, minDist));
    }

    private static double bruteForce(Point[] points) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                minDist = Math.min(minDist, points[i].distance(points[j]));
            }
        }
        return minDist;
    }

    private static double stripClosest(Point[] points, Point midPoint, double d) {
        // Create a list of points in the strip that are within the distance d from the vertical midline
        int n = points.length;
        Point[] strip = new Point[n];
        int j = 0;
        for (Point p : points) {  // Enhanced for loop
            if (Math.abs(p.getX() - midPoint.getX()) < d) {
                strip[j] = p;
                j++;
            }
        }

        // Sort the strip array by y-coordinate
        Arrays.sort(strip, 0, j, Comparator.comparingDouble(Point::getY));  // Using Comparator

        // Check the strip points for closer distances (7-8 neighbor scan)
        double minDist = d;
        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j && (strip[k].getY() - strip[i].getY()) < minDist; k++) {
                minDist = Math.min(minDist, strip[i].distance(strip[k]));
            }
        }

        return minDist;
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        };
        System.out.println("Closest pair distance: " + closestPair(points));
    }
}
