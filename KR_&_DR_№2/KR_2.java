import java.util.Scanner;

public class KR_2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Task_2_1 ===");
        Task_2_1.run();

        System.out.println("=== Task_2_2 ===");
        Task_2_2.run(sc);

        System.out.println("=== Task_2_3 ===");
        Task_2_3.run(sc);

        System.out.println("=== Task_2_4 ===");
        Task_2_4.run(sc);

        sc.close();
    }
}

class Task_2_1 {
    static void run() {
        int a = 0x0000001A;      
        int b = 0x80000001;      

        System.out.println("a = " + Integer.toBinaryString(a));
        System.out.println("b = " + Integer.toBinaryString(b));
        System.out.println("a AND b = " + Integer.toBinaryString(a & b));
        System.out.println("a OR b = " + Integer.toBinaryString(a | b));
        System.out.println("a XOR b = " + Integer.toBinaryString(a ^ b));
        System.out.println("NOT a = " + Integer.toBinaryString(~a));
        System.out.println("NOT b = " + Integer.toBinaryString(~b));
        System.out.println("a << 2 = " + Integer.toBinaryString(a << 2));
        System.out.println("b << 2 = " + Integer.toBinaryString(b << 2));
        System.out.println("a >> 2 = " + Integer.toBinaryString(a >> 2));
        System.out.println("b >> 2 = " + Integer.toBinaryString(b >> 2));
        System.out.println("a >>> 2 = " + Integer.toBinaryString(a >>> 2));
        System.out.println("b >>> 2 = " + Integer.toBinaryString(b >>> 2));
    }
}

class Task_2_2 {
    static void run(Scanner sc) {
        System.out.print("Enter number, bit position and bit value: ");
        int number = sc.nextInt();
        int position = sc.nextInt();
        int value = sc.nextInt();

        int mask = 1 << (position - 1);
        int result;
        if (value == 1) {
            result = number | mask;
        } else {
            result = number & (~mask);
        }

        System.out.println(result + " 0x" + Integer.toHexString(result).toUpperCase()
                + " " + Integer.toBinaryString(result));
    }
}

class Task_2_3 {
    static void run(Scanner sc) {
        System.out.print("N = ");
        int n = sc.nextInt();

        String digits = "";
        for (int i = 1; i <= n; i++) {
            digits = digits + i;
        }

        System.out.println("Permutations:");
        permute(digits, "");

        System.out.print("N K = ");
        int n2 = sc.nextInt();
        int k = sc.nextInt();

        if (k < 1 || k > n2) {
            System.out.println("Wrong N or K");
            return;
        }

        System.out.println("Combinations (" + n2 + ", " + k + "):");
        combine(1, n2, k, "");
    }

    static void permute(String remaining, String current) {
        if (remaining.length() == 0) {
            System.out.println(current);
            return;
        }
        for (int i = 0; i < remaining.length(); i++) {
            char c = remaining.charAt(i);
            String rest = remaining.substring(0, i) + remaining.substring(i + 1);
            permute(rest, current + c);
        }
    }

    static void combine(int start, int n, int k, String current) {
        if (k == 0) {
            System.out.println(current.trim());
            return;
        }
        for (int i = start; i <= n; i++) {
            combine(i + 1, n, k - 1, current + i + " ");
        }
    }
}

class Task_2_4 {
    static void run(Scanner sc) {
        System.out.print("Enter a number: ");
        int number = sc.nextInt();

        if (number < 1) {
            System.out.println("Число не натуральне!");
        } else {
            System.out.println("Factorial (loop) = " + factorialLoop(number));
            System.out.println("Factorial (recursion) = " + factorialRecursion(number));
        }
    }

    static long factorialLoop(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    static long factorialRecursion(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorialRecursion(n - 1);
    }
}