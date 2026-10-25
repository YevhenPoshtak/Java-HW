import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_5_9 (words in a text file)");
            System.out.println("2 - Task_6_6 (swap '0' and '1' from F to G)");
            System.out.println("3 - Task_6_7 (sum of real numbers from a binary file)");
            System.out.println("4 - Task_6_8 (Faculty and Institute serialization)");
            System.out.println("5 - Additional_Task (average of integers)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    Task_5_9.menu(in);
                }
                else if (choice.equals("2")) {
                    Task_6_6.menu(in);
                }
                else if (choice.equals("3")) {
                    Task_6_7.menu(in);
                }
                else if (choice.equals("4")) {
                    Task_6_8.menu(in);
                }
                else if (choice.equals("5")) {
                    Additional_Task.menu(in);
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

    static void printFile(String fileName) throws IOException {
        System.out.println("File " + fileName + ":");
        for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            System.out.println("  " + line);
        }
    }
}


class Task_5_9 {

    static int countWords(String fileName) throws IOException {
        int wordCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                for (String word : line.trim().split("\\s+")) {
                    if (!word.isEmpty()) {
                        wordCount++;
                    }
                }
            }
        }
        return wordCount;
    }

    static boolean isOneLetter(String word) {
        return word.length() == 1 && Character.isLetter(word.charAt(0));
    }

    static void cleanFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            for (String line : lines) {
                List<String> words = new ArrayList<>();
                for (String word : line.trim().split("\\s+")) {
                    if (!word.isEmpty() && !isOneLetter(word)) {
                        words.add(word);
                    }
                }
                bufferedWriter.write(String.join(" ", words));
                bufferedWriter.newLine();
            }
        }
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_5_9 -----");
        System.out.println("Enter the text (empty line to finish):");
        Task_6_6.writeFile("input.txt", Main.readText(in));

        System.out.println("Word count: " + countWords("input.txt"));
        cleanFile("input.txt");
        Main.printFile("input.txt");
    }
}


class Task_6_6 {

    static void writeFile(String fileName, String content) throws IOException {
        Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    static void replaceOneWithZero(String fileInputName, String fileOutputName) throws IOException {
        Path inputPath = Paths.get(fileInputName);
        if (!Files.exists(inputPath)) {
            throw new FileNotFoundException("Input file does not exist");
        }
        StringBuilder sb = new StringBuilder();
        for (String line : Files.readAllLines(inputPath, StandardCharsets.UTF_8)) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '1') {
                    sb.append('0');
                }
                else if (c == '0') {
                    sb.append('1');
                }
                else {
                    sb.append(c);
                }
            }
            sb.append(System.lineSeparator());
        }
        writeFile(fileOutputName, sb.toString());
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_6_6 -----");
        System.out.println("Enter lines of file F (empty line to finish):");
        writeFile("F.txt", Main.readText(in));

        replaceOneWithZero("F.txt", "G.txt");
        Main.printFile("G.txt");
    }
}


class Task_6_7 {

    static void writeNumbersToFile(String fileName, String line) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (String number : line.trim().split("\\s+")) {
                dos.writeDouble(Double.parseDouble(number));
            }
        }
    }

    static double sumNumbersFromFile(String fileName) throws IOException {
        double sum = 0;
        try (DataInputStream dis = new DataInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (dis.available() > 0) {
                double number = dis.readDouble();
                System.out.println("Read number: " + number);
                sum += number;
            }
        }
        return sum;
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Task_6_7 -----");
        System.out.print("Enter real numbers in one line: ");
        writeNumbersToFile("numbers.bin", in.nextLine());
        System.out.println("Sum of numbers: " + sumNumbersFromFile("numbers.bin"));
    }
}


class Faculty implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    Faculty(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}


class Institute implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Faculty[] faculty = new Faculty[0];

    Institute(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    Faculty[] getFaculty() {
        return faculty;
    }

    void addFaculty(Faculty newFaculty) {
        Faculty[] array = new Faculty[faculty.length + 1];
        System.arraycopy(faculty, 0, array, 0, faculty.length);
        array[faculty.length] = newFaculty;
        faculty = array;
    }

    void print() {
        System.out.println("Institute: " + name);
        for (Faculty f : faculty) {
            System.out.println("  " + f.getName());
        }
        System.out.println("Total faculties: " + faculty.length);
    }
}


class Task_6_8 {

    static void serialize(Institute institute, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(institute);
        }
    }

    static Institute deserialize(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Institute) ois.readObject();
        }
    }

    static void menu(Scanner in) throws Exception {
        System.out.println("\n----- Task_6_8 -----");
        System.out.print("Enter the institute name: ");
        Institute institute = new Institute(in.nextLine().trim());

        System.out.println("Enter faculty names (empty line to finish):");
        String line;
        while (!(line = in.nextLine().trim()).isEmpty()) {
            institute.addFaculty(new Faculty(line));
        }

        serialize(institute, "institute.ser");
        System.out.println("Serialized to institute.ser");

        System.out.println("Deserialized from institute.ser:");
        deserialize("institute.ser").print();
    }
}


class Additional_Task {

    static void write(String fileName, String line) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName))) {
            for (String number : line.trim().split("\\s+")) {
                out.writeInt(Integer.parseInt(number));
            }
        }
    }

    static void menu(Scanner in) throws IOException {
        System.out.println("\n----- Additional_Task -----");
        System.out.print("Enter integers in one line: ");
        write("numbers.dat", in.nextLine());

        int sum = 0;
        int count = 0;
        try (DataInputStream input = new DataInputStream(new FileInputStream("numbers.dat"));
             PrintWriter out = new PrintWriter("numbers.txt")) {
            while (input.available() > 0) {
                int x = input.readInt();
                out.print(x + " ");
                sum += x;
                count++;
            }
        }
        System.out.println("Written to numbers.txt, average: " + (double) sum / count);
    }
}
