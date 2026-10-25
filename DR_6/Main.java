import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));
        Task_5_12.menu(in);
    }
}


class Task_5_12 {

    static double length(int[] f) {
        return Math.hypot(f[3] - f[1], f[4] - f[2]);
    }

    static double perimeter(int[] f) {
        return 2.0 * (Math.abs(f[3] - f[1]) + Math.abs(f[2] - f[4]));
    }

    static double area(int[] f) {
        return Math.PI * f[3] * f[3];
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("Enter figures (empty line to finish):");
        try (PrintWriter out = new PrintWriter("figures.txt")) {
            String line;
            while (!(line = in.nextLine().trim()).isEmpty()) {
                out.println(line);
            }
        }

        int[] segment = null;
        int[] rectangle = null;
        int[] circle = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("figures.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int[] f = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    f[i] = Integer.parseInt(parts[i]);
                }

                if (f[0] == 1 && (segment == null || length(f) > length(segment))) {
                    segment = f;
                }
                else if (f[0] == 2 && (rectangle == null || perimeter(f) > perimeter(rectangle))) {
                    rectangle = f;
                }
                else if (f[0] == 3 && (circle == null || area(f) < area(circle))) {
                    circle = f;
                }
            }
        }

        if (segment != null) {
            System.out.println("Longest segment: " + Arrays.toString(segment) + ", length = " + length(segment));
        }
        if (rectangle != null) {
            System.out.println("Rectangle with the largest perimeter: " + Arrays.toString(rectangle) + ", perimeter = " + perimeter(rectangle));
        }
        if (circle != null) {
            System.out.println("Circle with the smallest area: " + Arrays.toString(circle) + ", area = " + area(circle));
        }
    }
}
