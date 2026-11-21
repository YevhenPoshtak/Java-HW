import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));
        try {
            Task_5.menu(in);
        }
        catch (Exception e) {
            System.out.println("Wrong data: " + e.getMessage());
        }
    }
}


class Task_5 {

    static double[] parse(String line) {
        return Arrays.stream(line.trim().split("[\\s,\\[\\]]+"))
                .filter(s -> !s.isEmpty())
                .mapToDouble(s -> {
                    double x = Double.parseDouble(s);
                    if (!Double.isFinite(x)) {
                        throw new IllegalArgumentException("not a finite number: " + s);
                    }
                    return x;
                })
                .toArray();
    }

    static int[] localMaxima(double[] a) {
        return IntStream.range(1, a.length - 1).filter(i -> a[i] > a[i - 1] && a[i] > a[i + 1]).toArray();
    }

    static int[] localMinima(double[] a) {
        return IntStream.range(1, a.length - 1).filter(i -> a[i] < a[i - 1] && a[i] < a[i + 1]).toArray();
    }

    static int[] longestIncreasing(double[] a) {
        int[] starts = IntStream.range(0, a.length).filter(i -> i == 0 || a[i] <= a[i - 1]).toArray();
        int[] lengths = IntStream.range(0, starts.length)
                .map(i -> (i + 1 < starts.length ? starts[i + 1] : a.length) - starts[i])
                .toArray();
        int best = IntStream.range(0, starts.length).reduce((i, j) -> lengths[j] > lengths[i] ? j : i).getAsInt();
        return new int[]{starts[best], starts[best] + lengths[best] - 1};
    }

    static void printPoints(String title, double[] a, int[] indexes) {
        System.out.println(title + ":");
        if (indexes.length == 0) {
            System.out.println("  none");
        }
        Arrays.stream(indexes).forEach(i -> System.out.println("  " + a[i] + " at index " + i));
    }

    static void menu(Scanner in) {
        System.out.println("Enter the sequence in one line:");
        double[] a = parse(in.nextLine());
        if (a.length == 0) {
            System.out.println("The sequence must not be empty.");
            return;
        }

        int[] maxima = localMaxima(a);
        int[] minima = localMinima(a);
        printPoints("Local maxima", a, maxima);
        printPoints("Local minima", a, minima);
        System.out.println("Transitions increasing -> decreasing: " + maxima.length);
        System.out.println("Transitions decreasing -> increasing: " + minima.length);

        int[] run = longestIncreasing(a);
        System.out.println("Longest strictly increasing fragment: indexes " + run[0] + ".." + run[1]
                + ", length " + (run[1] - run[0] + 1)
                + ", elements " + Arrays.toString(Arrays.copyOfRange(a, run[0], run[1] + 1)));
    }
}
