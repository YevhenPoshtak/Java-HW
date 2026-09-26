import java.util.Locale;
import java.util.Scanner;

public class DR_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        System.out.print("Enter a real number x: ");
        double x = scanner.nextDouble();
        scanner.close();

        printManualResults(x);
        printMathResults(x);
    }

    static long manualIntPart(double x) {
        return (long) x;
    }

    static double manualFracPart(double x) {
        return x - manualIntPart(x);
    }

    static long manualFloor(double x) {
        long intPart = manualIntPart(x);
        double frac = manualFracPart(x);
        if (x >= 0) {
            return intPart;
        }
        if (frac == 0) {
            return intPart;
        }
        return intPart - 1;
    }

    static long manualCeil(double x) {
        long intPart = manualIntPart(x);
        double frac = manualFracPart(x);
        if (x < 0) {
            return intPart;
        }
        if (frac == 0) {
            return intPart;
        }
        return intPart + 1;
    }

    static long manualRound(double x) {
        if (x >= 0) {
            return (long) (x + 0.5);
        }
        return (long) (x - 0.5);
    }

    static double mathIntPart(double x) {
        if (x < 0) {
            return Math.ceil(x);
        }
        return Math.floor(x);
    }

    static double mathFracPart(double x) {
        return x - mathIntPart(x);
    }

    static double mathFloor(double x) {
        return Math.floor(x);
    }

    static double mathCeil(double x) {
        return Math.ceil(x);
    }

    static long mathRound(double x) {
        return Math.round(x);
    }

    static void printManualResults(double x) {
        System.out.println();
        System.out.println("Manual calculation:");
        System.out.println("Integer part: " + manualIntPart(x));
        System.out.println("Fractional part: " + manualFracPart(x));
        System.out.println("Floor (largest integer less than x): " + manualFloor(x));
        System.out.println("Ceiling (smallest integer greater than x): " + manualCeil(x));
        System.out.println("Rounded value: " + manualRound(x));
    }

    static void printMathResults(double x) {
        System.out.println();
        System.out.println("Calculation using Math functions:");
        System.out.println("Integer part: " + mathIntPart(x));
        System.out.println("Fractional part: " + mathFracPart(x));
        System.out.println("Floor (Math.floor): " + mathFloor(x));
        System.out.println("Ceiling (Math.ceil): " + mathCeil(x));
        System.out.println("Rounded value (Math.round): " + mathRound(x));
    }
}