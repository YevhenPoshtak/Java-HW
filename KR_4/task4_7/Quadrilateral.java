package task4_7;

public class Quadrilateral {

    private static final double EPSILON = 1e-9;

    private final double[] x;
    private final double[] y;

    public Quadrilateral(double x1, double y1, double x2, double y2,
                         double x3, double y3, double x4, double y4) {
        x = new double[] {x1, x2, x3, x4};
        y = new double[] {y1, y2, y3, y4};
        for (int i = 0; i < 4; i++) {
            if (equal(cross(i, (i + 1) % 4, (i + 2) % 4), 0)) {
                throw new IllegalArgumentException(
                        "Three vertices lie on one line");
            }
        }
        if (intersect(0, 1, 2, 3)) {
            swap(1, 2);
        } else if (intersect(1, 2, 3, 0)) {
            swap(2, 3);
        }
    }

    private double cross(int i, int j, int k) {
        return (x[j] - x[i]) * (y[k] - y[i])
                - (y[j] - y[i]) * (x[k] - x[i]);
    }

    private boolean intersect(int a, int b, int c, int d) {
        return cross(a, b, c) * cross(a, b, d) < 0
                && cross(c, d, a) * cross(c, d, b) < 0;
    }

    private void swap(int i, int j) {
        double tempX = x[i];
        double tempY = y[i];
        x[i] = x[j];
        y[i] = y[j];
        x[j] = tempX;
        y[j] = tempY;
    }

    private double distance(int i, int j) {
        return Math.sqrt(Math.pow(x[i] - x[j], 2)
                + Math.pow(y[i] - y[j], 2));
    }

    private static boolean equal(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public double area() {
        double sum = 0;
        for (int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            sum += x[i] * y[j] - x[j] * y[i];
        }
        return Math.abs(sum) / 2;
    }

    public double perimeter() {
        return distance(0, 1) + distance(1, 2) + distance(2, 3)
                + distance(3, 0);
    }

    public String type() {
        double a = distance(0, 1);
        double b = distance(1, 2);
        double c = distance(2, 3);
        double d = distance(3, 0);
        boolean equalSides = equal(a, b) && equal(b, c) && equal(c, d);
        boolean equalDiagonals = equal(distance(0, 2), distance(1, 3));
        if (equalSides && equalDiagonals) {
            return "Square";
        }
        if (equalSides) {
            return "Rhombus";
        }
        if (equal(a, c) && equal(b, d) && equalDiagonals) {
            return "Rectangle";
        }
        return "Arbitrary";
    }

    @Override
    public String toString() {
        StringBuilder vertices = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            vertices.append(i == 0 ? "" : ", ").append("(").append(x[i])
                    .append("; ").append(y[i]).append(")");
        }
        return vertices + ": area = " + area()
                + ", perimeter = " + perimeter();
    }
}
