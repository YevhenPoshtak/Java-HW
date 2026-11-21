import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_1 (statistics of measurements from log*.txt files)");
            System.out.println("2 - Task_2 (prime numbers on a segment)");
            System.out.println("3 - Task_3 (matrix from a binary file)");
            System.out.println("4 - Task_4 (optimization candidates from a CSV file)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    Task_1.menu(in);
                }
                else if (choice.equals("2")) {
                    Task_2.menu(in);
                }
                else if (choice.equals("3")) {
                    Task_3.menu(in);
                }
                else if (choice.equals("4")) {
                    Task_4.menu(in);
                }
                else if (choice.equals("0")) {
                    System.out.println("Bye");
                    return;
                }
                else {
                    System.out.println("No such option, try again.");
                }
            }
            catch (Exception e) {
                System.out.println("Wrong data: " + e.getMessage());
            }
        }
    }

    static String readText(Scanner in) {
        StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = in.nextLine()).isEmpty()) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }

    static int parseInt(String text, String name) {
        try {
            return Integer.parseInt(text.trim());
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be an integer: " + text);
        }
    }

    static double parseFiniteDouble(String text, String name) {
        try {
            double value = Double.parseDouble(text.trim());
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(name + " must be a finite number: " + text);
            }
            return value;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a number: " + text);
        }
    }
}


class Task_1 {

    static boolean isMeasurementFile(Path path) {
        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.startsWith("log") && name.endsWith(".txt");
    }

    static List<Path> findFiles(Path root) throws IOException {
        try (Stream<Path> paths = Files.walk(root)) {
            return paths.filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                    .filter(Task_1::isMeasurementFile)
                    .sorted()
                    .toList();
        }
    }

    static void readMeasurements(Path file, List<Double> destination) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            destination.addAll(lines.filter(line -> !line.isBlank())
                    .map(line -> Main.parseFiniteDouble(line, "value"))
                    .toList());
        }
        catch (IllegalArgumentException e) {
            throw new IOException(file + ": " + e.getMessage());
        }
        catch (UncheckedIOException e) {
            throw new IOException("cannot read " + file + ": " + e.getCause().getMessage());
        }
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_1 -----");
        System.out.print("Enter the directory with measurement files: ");
        Path root = Paths.get(in.nextLine().trim());
        if (!Files.isDirectory(root)) {
            System.out.println("Directory does not exist or is not a directory: " + root);
            return;
        }
        System.out.print("Enter delta (>= 0): ");
        double delta = Main.parseFiniteDouble(in.nextLine(), "delta");
        if (delta < 0) {
            System.out.println("delta must be non-negative.");
            return;
        }

        List<Path> files = findFiles(root);
        List<Double> measurements = new ArrayList<>();
        for (Path file : files) {
            readMeasurements(file, measurements);
        }
        System.out.println("Files found: " + files.size());
        files.forEach(file -> System.out.println("  " + file));
        if (measurements.isEmpty()) {
            System.out.println("There are no measurements in the found files.");
            return;
        }

        double[] values = measurements.stream().mapToDouble(Double::doubleValue).toArray();
        DoubleSummaryStatistics statistics = Arrays.stream(values).summaryStatistics();
        double mean = statistics.getAverage();
        System.out.println("Count: " + statistics.getCount());
        System.out.println("Mean: " + mean);
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Max: " + statistics.getMax());
        System.out.println("Sorted: " + Arrays.toString(Arrays.stream(values).sorted().toArray()));
        System.out.println("Deviation from the mean greater than " + delta + ": "
                + Arrays.toString(Arrays.stream(values).filter(x -> Math.abs(x - mean) > delta).toArray()));

        if (statistics.getMin() == statistics.getMax()) {
            System.out.println("Mean without min and max: " + statistics.getMin());
        }
        else if (statistics.getCount() <= 2) {
            System.out.println("Mean without min and max cannot be calculated: no values are left.");
        }
        else {
            double trimmed = (statistics.getSum() - statistics.getMin() - statistics.getMax())
                    / (statistics.getCount() - 2);
            System.out.println("Mean without min and max: " + trimmed);
        }
    }
}


class Task_2 {

    static final int MAX_INTERVAL_SIZE = 10_000_000;

    record TwinPair(int first, int second) {}

    static int[] findPrimes(int start, int end) {
        int size = (int) ((long) end - start + 1);
        boolean[] composite = new boolean[size];
        int limit = (int) Math.sqrt(end);
        boolean[] baseComposite = new boolean[limit + 1];
        for (int candidate = 2; candidate <= limit; candidate++) {
            if (!baseComposite[candidate]) {
                if ((long) candidate * candidate <= limit) {
                    for (int multiple = candidate * candidate; multiple <= limit; multiple += candidate) {
                        baseComposite[multiple] = true;
                    }
                }
                long firstMultiple = Math.max((long) candidate * candidate,
                        ((long) start + candidate - 1) / candidate * candidate);
                for (long multiple = firstMultiple; multiple <= end; multiple += candidate) {
                    composite[(int) (multiple - start)] = true;
                }
            }
        }
        return IntStream.range(0, size)
                .map(index -> index + start)
                .filter(number -> number >= 2 && !composite[number - start])
                .toArray();
    }

    static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        for (long divisor = 3; divisor <= number / divisor; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    static long reverseDigits(int number) {
        long remaining = number;
        long reversed = 0;
        while (remaining > 0) {
            reversed = reversed * 10 + remaining % 10;
            remaining /= 10;
        }
        return reversed;
    }

    static void menu(Scanner in) {
        System.out.println("\n----- Task_2 -----");
        System.out.print("Enter a and b (2 <= a <= b): ");
        String[] parts = in.nextLine().trim().split("\\s+");
        int a = Main.parseInt(parts[0], "a");
        int b = Main.parseInt(parts[1], "b");
        if (a < 2 || a > b) {
            System.out.println("Bounds must satisfy 2 <= a <= b.");
            return;
        }
        long intervalSize = (long) b - a + 1;
        if (intervalSize > MAX_INTERVAL_SIZE) {
            System.out.println("The interval contains " + intervalSize + " numbers; the maximum supported size is "
                    + MAX_INTERVAL_SIZE + ".");
            return;
        }

        int[] primes = findPrimes(a, b);
        List<TwinPair> twinPairs = IntStream.range(0, primes.length - 1)
                .filter(i -> primes[i + 1] - primes[i] == 2)
                .mapToObj(i -> new TwinPair(primes[i], primes[i + 1]))
                .toList();
        List<String> reversiblePrimes = Arrays.stream(primes)
                .filter(p -> reverseDigits(p) != p && isPrime(reverseDigits(p)))
                .mapToObj(p -> "(" + p + ", " + reverseDigits(p) + ")")
                .toList();

        System.out.println("Prime numbers: " + Arrays.toString(primes));
        System.out.println("Count: " + primes.length);
        System.out.println("Twin-prime pairs: " + twinPairs);
        System.out.println("Primes whose reversed digits also form a different prime: " + reversiblePrimes);
    }
}


class Task_3 {

    record Cell(int row, int column, double value) {}

    static double[][] readMatrix(Path file) throws IOException {
        try (DataInputStream input = new DataInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
            int rows;
            int columns;
            try {
                rows = input.readInt();
                columns = input.readInt();
            }
            catch (EOFException e) {
                throw new IOException("Invalid matrix file: dimensions are missing: " + file);
            }
            if (rows <= 0 || columns <= 0) {
                throw new IOException("The row and column counts must be positive: " + file);
            }
            long expectedSize;
            try {
                long elementCount = Math.multiplyExact((long) rows, columns);
                long dataSize = Math.multiplyExact(elementCount, Double.BYTES);
                expectedSize = Math.addExact(2L * Integer.BYTES, dataSize);
            }
            catch (ArithmeticException e) {
                throw new IOException("Matrix dimensions are too large: " + file);
            }
            if (Files.size(file) != expectedSize) {
                throw new IOException("File size does not match the declared matrix dimensions: " + file);
            }

            double[][] matrix = new double[rows][columns];
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    double value = input.readDouble();
                    if (!Double.isFinite(value)) {
                        throw new IOException("Non-finite value at position (" + row + ", " + column
                                + ") in file: " + file);
                    }
                    matrix[row][column] = value;
                }
            }
            return matrix;
        }
        catch (EOFException e) {
            throw new IOException("Unexpected end of matrix file: " + file);
        }
    }

    static void writeMatrix(Path file, double[][] matrix) throws IOException {
        try (DataOutputStream output = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            output.writeInt(matrix.length);
            output.writeInt(matrix[0].length);
            for (double[] row : matrix) {
                for (double value : row) {
                    output.writeDouble(value);
                }
            }
        }
    }

    static double[][] inputMatrix(Scanner in) {
        System.out.print("Enter the number of rows and columns: ");
        String[] size = in.nextLine().trim().split("\\s+");
        int rows = Main.parseInt(size[0], "rows");
        int columns = Main.parseInt(size[1], "columns");

        double[][] matrix = new double[rows][columns];
        System.out.println("Enter the matrix (" + rows + " lines of " + columns + " numbers):");
        for (int row = 0; row < rows; row++) {
            String[] parts = in.nextLine().trim().split("\\s+");
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = Main.parseFiniteDouble(parts[column], "element");
            }
        }
        return matrix;
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_3 -----");
        System.out.print("Enter the path to a .bin file (empty line to type a matrix into matrix.bin): ");
        String name = in.nextLine().trim();
        Path file;
        if (name.isEmpty()) {
            file = Paths.get("matrix.bin");
            writeMatrix(file, inputMatrix(in));
        }
        else {
            file = Paths.get(name);
            if (!Files.isRegularFile(file)) {
                System.out.println("File does not exist: " + file);
                return;
            }
        }
        System.out.print("Enter k: ");
        double scalar = Main.parseFiniteDouble(in.nextLine(), "k");
        double[][] matrix = readMatrix(file);
        int columns = matrix[0].length;

        double[] values = Arrays.stream(matrix).flatMapToDouble(Arrays::stream).toArray();
        DoubleSummaryStatistics statistics = Arrays.stream(values).summaryStatistics();
        long positive = Arrays.stream(values).filter(x -> x > 0).count();
        long negative = Arrays.stream(values).filter(x -> x < 0).count();
        long zero = Arrays.stream(values).filter(x -> x == 0.0).count();
        double[] rowSums = Arrays.stream(matrix).mapToDouble(row -> Arrays.stream(row).sum()).toArray();
        Cell largest = IntStream.range(0, values.length)
                .mapToObj(i -> new Cell(i / columns, i % columns, values[i]))
                .reduce((first, second) -> Math.abs(second.value()) > Math.abs(first.value()) ? second : first)
                .get();
        double[][] scaled = Arrays.stream(matrix)
                .map(row -> Arrays.stream(row).map(x -> x * scalar).toArray())
                .toArray(double[][]::new);

        System.out.println("Matrix:");
        Arrays.stream(matrix).forEach(row -> System.out.println(Arrays.toString(row)));
        System.out.println("Sum: " + statistics.getSum());
        System.out.println("Mean: " + statistics.getAverage());
        System.out.println("Positive values: " + positive + ", negative values: " + negative
                + ", zero values: " + zero);
        System.out.println("Row sums: " + Arrays.toString(rowSums));
        System.out.println("Element with the greatest absolute value: " + largest.value()
                + " at position (" + largest.row() + ", " + largest.column() + ")");
        System.out.println("Matrix multiplied by " + scalar + ":");
        Arrays.stream(scaled).forEach(row -> System.out.println(Arrays.toString(row)));
        System.out.println("Elements in ascending order: " + Arrays.toString(Arrays.stream(values).sorted().toArray()));
    }
}


class Task_4 {

    record Solution(double x, double y, double objective) {}

    static final Comparator<Solution> ORDER = Comparator.comparingDouble(Solution::objective)
            .thenComparingDouble(Solution::x)
            .thenComparingDouble(Solution::y);

    static List<Solution> readSolutions(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isBlank())
                    .filter(line -> !isHeader(line.split(",", -1)))
                    .map(line -> {
                        String[] fields = line.split(",", -1);
                        if (fields.length != 3) {
                            throw new IllegalArgumentException("expected exactly three CSV columns x,y,objective: "
                                    + line);
                        }
                        return new Solution(Main.parseFiniteDouble(fields[0], "x"),
                                Main.parseFiniteDouble(fields[1], "y"),
                                Main.parseFiniteDouble(fields[2], "objective"));
                    })
                    .toList();
        }
        catch (IllegalArgumentException e) {
            throw new IOException(file + ": " + e.getMessage());
        }
        catch (UncheckedIOException e) {
            throw new IOException("cannot read " + file + ": " + e.getCause().getMessage());
        }
    }

    static boolean isHeader(String[] fields) {
        return fields.length == 3
                && fields[0].trim().equalsIgnoreCase("x")
                && fields[1].trim().equalsIgnoreCase("y")
                && fields[2].trim().equalsIgnoreCase("objective");
    }

    static String objectiveGroup(double objective) {
        if (objective < 0) {
            return "objective < 0";
        }
        if (objective < 10) {
            return "0 <= objective < 10";
        }
        if (objective < 100) {
            return "10 <= objective < 100";
        }
        return "objective >= 100";
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_4 -----");
        System.out.print("Enter the path to a CSV file (empty line to type candidates into candidates.csv): ");
        String name = in.nextLine().trim();
        Path file;
        if (name.isEmpty()) {
            file = Paths.get("candidates.csv");
            System.out.println("Enter candidates as \"x,y,objective\" (empty line to finish):");
            Files.writeString(file, Main.readText(in), StandardCharsets.UTF_8);
        }
        else {
            file = Paths.get(name);
            if (!Files.isRegularFile(file)) {
                System.out.println("File does not exist: " + file);
                return;
            }
        }
        System.out.print("Enter K (> 0) and L: ");
        String[] parts = in.nextLine().trim().split("\\s+");
        int limit = Main.parseInt(parts[0], "K");
        double threshold = Main.parseFiniteDouble(parts[1], "L");
        if (limit <= 0) {
            System.out.println("K must be a positive integer.");
            return;
        }

        List<Solution> feasible = readSolutions(file).stream()
                .filter(solution -> solution.x() >= 0 && solution.y() >= 0 && solution.x() + solution.y() <= 1)
                .toList();
        Map<String, Long> counts = new LinkedHashMap<>();
        counts.put("objective < 0", 0L);
        counts.put("0 <= objective < 10", 0L);
        counts.put("10 <= objective < 100", 0L);
        counts.put("objective >= 100", 0L);
        counts.putAll(feasible.stream().collect(Collectors.groupingBy(
                solution -> objectiveGroup(solution.objective()), Collectors.counting())));

        System.out.println("Number of feasible candidates: " + feasible.size());
        if (feasible.isEmpty()) {
            System.out.println("There are no feasible candidates.");
            return;
        }
        System.out.println("Best candidate: " + feasible.stream().min(ORDER).get());
        System.out.println("Up to " + limit + " best candidates:");
        feasible.stream().sorted(ORDER).limit(limit).forEach(solution -> System.out.println("  " + solution));
        System.out.println("Number with objective <= " + threshold + ": "
                + feasible.stream().filter(solution -> solution.objective() <= threshold).count());
        System.out.println("Candidate counts by interval:");
        counts.forEach((group, count) -> System.out.println("  " + group + ": " + count));
    }
}
