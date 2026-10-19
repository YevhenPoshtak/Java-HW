import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_5_6 (string processing)");
            System.out.println("2 - Task_5_7 (add \"very\")");
            System.out.println("3 - Task_5_8 (sum of garbage strings)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            if (choice.equals("1")) {
                Task_5_6.menu(in);
            }
            else if (choice.equals("2")) {
                Task_5_7.menu(in);
            }
            else if (choice.equals("3")) {
                Task_5_8.menu(in);
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


class Task_5_6 {

    static String removeDigitsDoubleSigns(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                continue;
            }
            sb.append(c);
            if (c == '+' || c == '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    static String removePlusBeforeDigit(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextIsDigit = i + 1 < s.length() && Character.isDigit(s.charAt(i + 1));
            if (c == '+' && nextIsDigit) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    static String removeVAfterS(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean prevIsS = i > 0 && Character.toLowerCase(s.charAt(i - 1)) == 'с';
            if (Character.toLowerCase(c) == 'в' && prevIsS) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    static String replacePhWithF(String s) {
        return s.replace("ph", "f").replace("pH", "f").replace("Ph", "F").replace("PH", "F");
    }


    static String removeExtraSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean prevIsSpace = i > 0 && s.charAt(i - 1) == ' ';
            if (c == ' ' && prevIsSpace) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    static void menu(Scanner in) {
        while (true) {
            System.out.println("\n----- Task_5_6 -----");
            System.out.println("1 - Remove digits and double '+' and '-'");
            System.out.println("2 - Remove '+' followed by a digit");
            System.out.println("3 - Remove 'в' preceded by 'с'");
            System.out.println("4 - Replace 'ph' with 'f'");
            System.out.println("5 - Remove extra spaces");
            System.out.println("0 - Back");
            System.out.print("Choose a method: ");

            String choice = in.nextLine().trim();

            if (choice.equals("0")) {
                return;
            }

            if (!choice.matches("[1-5]")) {
                System.out.println("No such method, try again.");
                continue;
            }

            System.out.print("Enter a string: ");
            String s = in.nextLine();

            String result;

            if (choice.equals("1")) {
                result = removeDigitsDoubleSigns(s);
            }
            else if (choice.equals("2")) {
                result = removePlusBeforeDigit(s);
            }
            else if (choice.equals("3")) {
                result = removeVAfterS(s);
            }
            else if (choice.equals("4")) {
                result = replacePhWithF(s);
            }
            else {
                result = removeExtraSpaces(s);
            }
            System.out.println("Result: " + result);
        }
    }
}


class Task_5_7 {

    static String addVery(String s) {
        StringBuilder sb = new StringBuilder();
        for (String word : s.split(" ")) {
            String w = onlyLetters(word).toLowerCase();
            if (w.equals("small") || w.equals("large")) {
                sb.append("very ");
            }
            sb.append(word).append(" ");
        }
        return sb.toString().trim();
    }


    static String onlyLetters(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(c);
            }
            else if (sb.length() > 0) {
                break;
            }
        }
        return sb.toString();
    }


    static void menu(Scanner in) {
        System.out.println("\n----- Task_5_7 -----");
        System.out.print("Enter a string: ");
        String s = in.nextLine();
        System.out.println("Result: " + addVery(s));
    }
}


class Task_5_8 {

    static int clean(String s) {
        if (s == null) {
            return 0;
        }
        int total = 0;
        for (String number : s.split("\\D+")) {
            if (!number.isEmpty()) {
                total = Math.addExact(total, Integer.parseInt(number));
            }
        }
        return total;
    }


    static int sum(String a, String b) {
        return Math.addExact(clean(a), clean(b));
    }


    static void menu(Scanner in) {
        System.out.println("\n----- Task_5_8 -----");
        System.out.print("Enter the first string: ");
        String a = in.nextLine();
        System.out.print("Enter the second string: ");
        String b = in.nextLine();
        try {
            System.out.println("Result: " + sum(a, b));
        }
        catch (NumberFormatException | ArithmeticException e) {
            System.out.println("The number is too large for int.");
        }
    }
}
