import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_7_14 (largest submatrix of equal elements)");
            System.out.println("2 - Task_7_2 (digits in reverse order via stack)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    Task_7_14.menu(in);
                }
                else if (choice.equals("2")) {
                    Task_7_2.menu(in);
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
}


class Task_7_14 {

    static int[] largestSubmatrix(int[][] a) {
        int rows = a.length;
        int cols = a[0].length;
        int[] heights = new int[cols];
        int[] best = {0, 0, 0, 0, 0};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                heights[j] = (i > 0 && a[i - 1][j] == a[i][j]) ? heights[j] + 1 : 1;
            }

            int start = 0;
            while (start < cols) {
                int end = start;
                while (end + 1 < cols && a[i][end + 1] == a[i][start]) {
                    end++;
                }
                scanRun(a[i][start], heights, start, end, i, best);
                start = end + 1;
            }
        }
        return best;
    }

    static void scanRun(int value, int[] heights, int from, int to, int row, int[] best) {
        Stack<Integer> stack = new Stack<>();
        for (int j = from; j <= to + 1; j++) {
            int h = (j == to + 1) ? 0 : heights[j];
            while (!stack.isEmpty() && heights[stack.peek()] >= h) {
                int height = heights[stack.pop()];
                int left = stack.isEmpty() ? from : stack.peek() + 1;
                int width = j - left;
                if (height * width > best[0]) {
                    best[0] = height * width;
                    best[1] = row - height + 1;
                    best[2] = left;
                    best[3] = row;
                    best[4] = value;
                }
            }
            stack.push(j);
        }
    }

    static void menu(Scanner in) {
        System.out.println("\n----- Task_7_14 -----");
        System.out.print("Enter the number of rows and columns: ");
        String[] size = in.nextLine().trim().split("\\s+");
        int rows = Integer.parseInt(size[0]);
        int cols = Integer.parseInt(size[1]);

        int[][] a = new int[rows][cols];
        System.out.println("Enter the matrix (" + rows + " lines of " + cols + " integers):");
        for (int i = 0; i < rows; i++) {
            String[] parts = in.nextLine().trim().split("\\s+");
            for (int j = 0; j < cols; j++) {
                a[i][j] = Integer.parseInt(parts[j]);
            }
        }

        int[] best = largestSubmatrix(a);
        int top = best[1];
        int left = best[2];
        int bottom = best[3];
        int right = left + best[0] / (bottom - top + 1) - 1;

        System.out.println("Value: " + best[4] + ", elements: " + best[0]);
        System.out.println("Rows " + (top + 1) + ".." + (bottom + 1) + ", columns " + (left + 1) + ".." + (right + 1));
    }
}


class Task_7_2 {

    static String reverseDigits(String number) {
        Stack<Character> stack = new Stack<>();
        for (char c : number.toCharArray()) {
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    static void menu(Scanner in) {
        System.out.println("\n----- Task_7_2 -----");
        System.out.print("Enter a number: ");
        String text = in.nextLine().trim();
        long number = Long.parseLong(text);

        String reversed = reverseDigits(Long.toString(number).replace("-", "")).replaceFirst("^0+(?=.)", "");
        System.out.println("Reversed: " + (number < 0 ? "-" : "") + reversed);
    }
}
