import java.util.Scanner;

public class DR_3 {
    public static void main(String[] args) {
        System.out.println("=== Task_3_2 ===");
        Task_3_2.run(new Scanner(System.in));

        System.out.println("=== Task_3_15 ===");
        Task_3_15.run(args);
    }
}

class Task_3_2 {
    static void run(Scanner sc) {
        System.out.println("Enter integers, end with empty line:");

        String s = "";
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }
            s += line + " ";
        }

        String[] t = new String[0];
        if (!s.isEmpty()) {
            t = s.trim().split("\\s+");
        }

        long[] a = new long[t.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = Long.parseLong(t[i]);
        }

        long val = 0;
        int cnt = 0;
        int i = 0;
        while (i < a.length) {
            int j = i;
            while (j < a.length && a[j] == a[i]) {
                j++;
            }

            if (j - i > cnt || (j - i == cnt && a[i] > val)) {
                cnt = j - i;
                val = a[i];
            }
            i = j;
        }

        System.out.println(cnt + " numbers in a row " + val);
    }
}

class Task_3_15 {
    static void run(String[] args) {
        long n = Long.parseLong(args[0]);
        long tmp = n;
        int odd = 0;
        int even = 0;

        for (int i = 0; i < 11; i++) {
            if (i % 2 == 0) {
                even += tmp % 10;
            } else {
                odd += tmp % 10;
            }
            tmp /= 10;
        }

        int check = (10 - (odd + 3 * even) % 10) % 10;

        System.out.println("UPC: " + String.format("%011d", n) + check);
    }
}
