import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_7_6 (index stack for a binary file)");
            System.out.println("2 - Task_7_7 (sort lines with Collections.sort)");
            System.out.println("3 - Task_7_8 (relative frequency of words)");
            System.out.println("4 - Task_7_9 (counting-out game: ArrayList vs LinkedList)");
            System.out.println("5 - Task_7_10 (lines through several points)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    Task_7_6.menu(in);
                }
                else if (choice.equals("2")) {
                    Task_7_7.menu(in);
                }
                else if (choice.equals("3")) {
                    Task_7_8.menu(in, args);
                }
                else if (choice.equals("4")) {
                    Task_7_9.menu(in);
                }
                else if (choice.equals("5")) {
                    Task_7_10.menu(in);
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
                System.out.println("Wrong data.");
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

    static void writeFile(String fileName, String content) throws IOException {
        Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8);
    }
}


class Task_7_6 {

    static Stack<Long> buildIndex(String fileName) throws IOException {
        Stack<Long> index = new Stack<>();
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            while (file.getFilePointer() < file.length()) {
                index.push(file.getFilePointer());
                file.readUTF();
                file.readDouble();
            }
        }
        return index;
    }

    static String readRecord(String fileName, Stack<Long> index, int number) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            file.seek(index.get(number - 1));
            String name = file.readUTF();
            double value = file.readDouble();
            return name + " " + value;
        }
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_7_6 -----");
        System.out.println("Enter records as \"name value\" (empty line to finish):");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("records.bin"))) {
            String line;
            while (!(line = in.nextLine().trim()).isEmpty()) {
                String[] parts = line.split("\\s+");
                out.writeUTF(parts[0]);
                out.writeDouble(Double.parseDouble(parts[1]));
            }
        }

        Stack<Long> index = buildIndex("records.bin");
        System.out.println("Index stack (offsets): " + index);

        while (true) {
            System.out.print("Record number (0 to stop): ");
            int number = Integer.parseInt(in.nextLine().trim());
            if (number == 0) {
                break;
            }
            if (number < 0 || number > index.size()) {
                System.out.println("No such record.");
                continue;
            }
            System.out.println("Record " + number + ": " + readRecord("records.bin", index, number));
        }
    }
}


class Task_7_7 {

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_7_7 -----");
        System.out.println("Enter lines (empty line to finish):");
        Main.writeFile("lines.txt", Main.readText(in));

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("lines.txt"), StandardCharsets.UTF_8));
        Collections.sort(lines);

        System.out.println("Sorted lines:");
        for (String line : lines) {
            System.out.println("  " + line);
        }
    }
}


class Task_7_8 {

    static Map<String, Double> frequencies(String text, List<String> words) {
        String[] textWords = text.toLowerCase().split("[^\\p{L}\\p{N}]+");
        int total = 0;
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String word : words) {
            counts.put(word.toLowerCase(), 0);
        }
        for (String word : textWords) {
            if (word.isEmpty()) {
                continue;
            }
            total++;
            if (counts.containsKey(word)) {
                counts.put(word, counts.get(word) + 1);
            }
        }

        Map<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            result.put(entry.getKey(), total == 0 ? 0.0 : (double) entry.getValue() / total);
        }
        return result;
    }

    static void menu(Scanner in, String[] args) {
        System.out.println("\n----- Task_7_8 -----");
        List<String> words;
        if (args.length > 0) {
            words = Arrays.asList(args);
        }
        else {
            System.out.print("Enter the words to search for in one line: ");
            words = Arrays.asList(in.nextLine().trim().split("\\s+"));
        }

        System.out.println("Enter the text (empty line to finish):");
        String text = Main.readText(in);

        for (Map.Entry<String, Double> entry : frequencies(text, words).entrySet()) {
            System.out.printf("%s: %.4f%n", entry.getKey(), entry.getValue());
        }
    }
}


class Task_7_9 {

    static long runArrayList(int n, int m, List<Integer> order) {
        long start = System.nanoTime();
        List<Integer> people = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }
        int position = 0;
        while (!people.isEmpty()) {
            position = (position + m - 1) % people.size();
            int removed = people.remove(position);
            if (order != null) {
                order.add(removed);
            }
        }
        return System.nanoTime() - start;
    }

    static long runLinkedList(int n, int m, List<Integer> order) {
        long start = System.nanoTime();
        List<Integer> people = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }
        int position = 0;
        while (!people.isEmpty()) {
            position = (position + m - 1) % people.size();
            int removed = people.remove(position);
            if (order != null) {
                order.add(removed);
            }
        }
        return System.nanoTime() - start;
    }

    static void menu(Scanner in) {
        System.out.println("\n----- Task_7_9 -----");
        System.out.print("Enter N (people) and m (words in the counting-out rhyme): ");
        String[] parts = in.nextLine().trim().split("\\s+");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);

        List<Integer> order = new ArrayList<>();
        long arrayListTime = runArrayList(n, m, order);
        long linkedListTime = runLinkedList(n, m, null);

        if (n <= 50) {
            System.out.println("Elimination order: " + order);
        }
        System.out.println("ArrayList:  " + arrayListTime / 1000 + " us");
        System.out.println("LinkedList: " + linkedListTime / 1000 + " us");
        System.out.println(arrayListTime < linkedListTime ? "ArrayList is faster." : "LinkedList is faster.");
        System.out.println("Each step needs the element at an index: ArrayList does this in O(1) but shifts the tail "
                + "on removal, LinkedList removes in O(1) but has to walk to the index in O(n).");
    }
}


class Task_7_10 {

    static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    static String lineKey(long[] p, long[] q) {
        long a = q[1] - p[1];
        long b = p[0] - q[0];
        long c = -(a * p[0] + b * p[1]);

        long g = gcd(gcd(a, b), c);
        a /= g;
        b /= g;
        c /= g;
        if (a < 0 || (a == 0 && b < 0)) {
            a = -a;
            b = -b;
            c = -c;
        }
        return (term(a, "x", true) + term(b, "y", false) + term(c, "", false)).replaceFirst("^ \\+ ", "") + " = 0";
    }

    static String term(long k, String var, boolean first) {
        if (k == 0) {
            return "";
        }
        String sign = k < 0 ? "-" : (first ? "" : "+");
        String body = (Math.abs(k) == 1 && !var.isEmpty() ? "" : String.valueOf(Math.abs(k))) + var;
        return first ? sign + body : " " + sign + " " + body;
    }

    static Map<String, Set<Integer>> findLines(List<long[]> points) {
        Map<String, Set<Integer>> lines = new HashMap<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                String key = lineKey(points.get(i), points.get(j));
                Set<Integer> set = lines.computeIfAbsent(key, k -> new TreeSet<>());
                set.add(i);
                set.add(j);
            }
        }
        return lines;
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_7_10 -----");
        System.out.print("Enter N: ");
        int n = Integer.parseInt(in.nextLine().trim());

        List<long[]> points = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        System.out.println("Enter " + n + " points as \"x y\" (one per line):");
        for (int i = 0; i < n; i++) {
            String[] parts = in.nextLine().trim().split("\\s+");
            long x = Long.parseLong(parts[0]);
            long y = Long.parseLong(parts[1]);
            if (seen.add(x + " " + y)) {
                points.add(new long[]{x, y});
            }
        }

        Map<String, Set<Integer>> lines = findLines(points);
        try (PrintWriter out = new PrintWriter("lines_result.txt", StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Set<Integer>> entry : lines.entrySet()) {
                out.println(entry.getKey() + " : " + entry.getValue().size() + " points");
            }
        }

        System.out.println("Written to lines_result.txt:");
        for (String line : Files.readAllLines(Paths.get("lines_result.txt"), StandardCharsets.UTF_8)) {
            System.out.println("  " + line);
        }
    }
}
