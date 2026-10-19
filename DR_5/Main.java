import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));
        Task_5_4.menu(in);
    }
}


class Task_5_4 {

    // Single letters don't count, otherwise any string would split into n letters trivially
    static final int MIN_LENGTH = 2;

    static boolean isSymmetric(String s) {
        if (s.length() < MIN_LENGTH) {
            return false;
        }
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    static String splitInTwo(String s) {
        for (int i = 1; i < s.length(); i++) {
            String left = s.substring(0, i);
            String right = s.substring(i);
            if (isSymmetric(left) && isSymmetric(right)) {
                return left + " | " + right;
            }
        }
        return null;
    }

    static String splitInN(String s, int n) {
        if (n == 1) {
            if (isSymmetric(s)) {
                return s;
            }
            return null;
        }
        for (int i = 1; i < s.length(); i++) {
            String left = s.substring(0, i);
            if (isSymmetric(left)) {
                String rest = splitInN(s.substring(i), n - 1);
                if (rest != null) {
                    return left + " | " + rest;
                }
            }
        }
        return null;
    }

    static void printResult(String parts) {
        if (parts == null) {
            System.out.println("Result: NO");
        }
        else {
            System.out.println("Result: YES (" + parts + ")");
        }
    }

    static void menu(Scanner in) {
        while (true) {
            System.out.println("\n----- Task_5_4 -----");
            System.out.println("1 - Check for 2 symmetric substrings");
            System.out.println("2 - Check for n symmetric substrings");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter a string: ");
                String s = in.nextLine();
                printResult(splitInTwo(s));
            }
            else if (choice.equals("2")) {
                System.out.print("Enter a string: ");
                String s = in.nextLine();
                System.out.print("Enter n: ");
                try {
                    int n = Integer.parseInt(in.nextLine().trim());
                    if (n < 1) {
                        System.out.println("n must be at least 1.");
                    }
                    else {
                        printResult(splitInN(s, n));
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("n must be a number.");
                }
            }
            else if (choice.equals("0")) {
                System.out.println("Bye");
                return;
            }
            else {
                System.out.println("No such option, try again.");
            }
        }
    }
}
