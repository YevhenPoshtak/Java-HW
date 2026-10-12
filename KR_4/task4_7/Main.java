package task4_7;

public class Main {

    public static void main(String[] args) {
        Quadrilateral[] list = {
            new Quadrilateral(0, 0, 2, 0, 2, 2, 0, 2),
            new Quadrilateral(0, 0, 5, 5, 5, 0, 0, 5),
            new Quadrilateral(0, 0, 4, 0, 4, 2, 0, 2),
            new Quadrilateral(0, 0, 6, 0, 6, 3, 0, 3),
            new Quadrilateral(0, 0, 3, 4, 6, 0, 3, -4),
            new Quadrilateral(0, 0, 1, 2, 2, 0, 1, -2),
            new Quadrilateral(0, 0, 5, 0, 4, 3, 1, 4),
            new Quadrilateral(0, 0, 2, 0, 3, 1, 0, 2)
        };
        String[] types = {"Square", "Rectangle", "Rhombus", "Arbitrary"};

        for (String type : types) {
            int count = 0;
            Quadrilateral maxArea = null;
            Quadrilateral minArea = null;
            Quadrilateral maxPerimeter = null;
            Quadrilateral minPerimeter = null;
            for (Quadrilateral q : list) {
                if (!q.type().equals(type)) {
                    continue;
                }
                count++;
                if (maxArea == null || q.area() > maxArea.area()) {
                    maxArea = q;
                }
                if (minArea == null || q.area() < minArea.area()) {
                    minArea = q;
                }
                if (maxPerimeter == null
                        || q.perimeter() > maxPerimeter.perimeter()) {
                    maxPerimeter = q;
                }
                if (minPerimeter == null
                        || q.perimeter() < minPerimeter.perimeter()) {
                    minPerimeter = q;
                }
            }
            System.out.println(type + ": " + count);
            if (count > 0) {
                System.out.println("  Max area: " + maxArea);
                System.out.println("  Min area: " + minArea);
                System.out.println("  Max perimeter: " + maxPerimeter);
                System.out.println("  Min perimeter: " + minPerimeter);
            }
        }

        try {
            new Quadrilateral(0, 0, 1, 1, 2, 2, 0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Degenerate quadrilateral rejected: "
                    + e.getMessage());
        }
    }
}
