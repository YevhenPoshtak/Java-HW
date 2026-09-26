import java.util.Scanner;
import java.util.Locale;

public class KR_1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        System.out.println("=== Task_1_1 ===");
        Task_1_1.run();

        System.out.println("=== Task_1_2 ===");
        Task_1_2.run(args);

        System.out.println("=== Task_1_3 ===");
        Task_1_3.run();

        System.out.println("=== Task_1_4 ===");
        Task_1_4.run(sc);

        System.out.println("=== Task_1_5 ===");
        Task_1_5.run(sc);

        System.out.println("=== Task_1_6 ===");
        Task_1_6.run(sc);

        sc.close();
    }
}

class Task_1_1 {
    int i;
    char c;
    String s;

    static void run() {
        Task_1_1 t = new Task_1_1();
        System.out.println("int = " + t.i);
        System.out.println("char = [" + t.c + "]");
        System.out.println("String = " + t.s);
        System.out.println("Hello, world!");
    }
}

class Task_1_2 {
    public static void run(String[] args) {
        if (args.length < 3) {
            System.out.println("Enter at least 3 arguments");
            return;
        }
        System.out.println("First argument: " + args[0]);
        System.out.println("Second argument: " + args[1]);
        System.out.println("Third argument: " + args[2]);

        double sum = 0;
        int count = 0;
        int i = 0;
        while (i < args.length) {
            try {
                sum += Double.parseDouble(args[i]);
                count++;
            } catch (NumberFormatException e) {
                System.out.println(args[i] + " is not a number");
            }
            i++;
        }
        System.out.println("Sum: " + sum);
        System.out.println("Real numbers entered: " + count);
    }
}

class Task_1_3 {
    static void run() {
        System.out.println("1.2 + 31 = " + (1.2 + 31));
        System.out.println("45*54-11 = " + (45 * 54 - 11));
        System.out.println("15/4 = " + (15 / 4));
        System.out.println("15.0/4 = " + (15.0 / 4));
        System.out.println("67%5 = " + (67 % 5));
        System.out.println("(2*45.1+3.2)/2 = " + ((2 * 45.1 + 3.2) / 2));
    }
}

class Task_1_4 {
    static void run(Scanner sc) {
        System.out.print("Enter a real number from 0 to 10000: ");
        double x = sc.nextDouble();
        double result = Math.pow(x, 8);
        System.out.printf(Locale.US, "%20.4f%n", result);
    }
}

class Task_1_5 {
    static double y1(double x) {
        double t = x * x + 1;
        return t * t;
    }

    static double y2(double x) {
        double x2 = x * x;
        return (x2 + x) * (x2 + 1) + 1;
    }

    static double y3(double x) {
        double t = x + 1;
        double t2 = t * t;
        double t4 = t2 * t2;
        return t4 * t;
    }

    static double y4(double x) {
        double x3 = x * x * x;
        double x9 = x3 * x3 * x3;
        return x9 + x3 + 1;
    }

    static double y5(double x) {
        double u = 2 * x;
        double u2 = u * u;
        return (u2 + u) * (u2 + 1) + 1;
    }

    static double y6(double x) {
        double x2 = x * x;
        return x * (x2 * x2 + x2 + 1);
    }

    static void run(Scanner sc) {
        System.out.print("Enter value x: ");
        double x = sc.nextDouble();
        System.out.println("y1 = " + y1(x));
        System.out.println("y2 = " + y2(x));
        System.out.println("y3 = " + y3(x));
        System.out.println("y4 = " + y4(x));
        System.out.println("y5 = " + y5(x));
        System.out.println("y6 = " + y6(x));
    }
}

class Task_1_6 {
    static double length(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    static double area(double a, double b, double c) {
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    static void checkGivenSides() {
        System.out.println("Checking given sides a=3, b=c=3.5+3*2^-111:");
        double a = 3;
        double b = 3.5 + 3 * Math.pow(2, -111);
        double c = 3.5 + 3 * Math.pow(2, -111);

        System.out.println("a = " + a + ", b = " + b + ", c = " + c);

        double perimeter = a + b + c;
        double s = area(a, b, c);

        System.out.println("Perimeter = " + perimeter);
        System.out.println("Area = " + s);
    }

    static void run(Scanner sc) {
        checkGivenSides();

        System.out.print("Enter coordinates of point A (x y): ");
        double ax = sc.nextDouble();
        double ay = sc.nextDouble();
        System.out.print("Enter coordinates of point B (x y): ");
        double bx = sc.nextDouble();
        double by = sc.nextDouble();
        System.out.print("Enter coordinates of point C (x y): ");
        double cx = sc.nextDouble();
        double cy = sc.nextDouble();

        double a = length(bx, by, cx, cy);
        double b = length(ax, ay, cx, cy);
        double c = length(ax, ay, bx, by);

        double perimeter = a + b + c;
        double s = area(a, b, c);

        System.out.println("Perimeter = " + perimeter);
        System.out.println("Area = " + s);
    }
}