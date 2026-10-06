import java.util.Arrays;
import java.util.Scanner;

public class KR_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Task_3_4 ===");
        Task_3_4.run(sc);

        System.out.println("=== Task_3_5 ===");
        Task_3_5.run(sc);

        System.out.println("=== Task_3_6 ===");
        Task_3_6.run(sc);

        System.out.println("=== Task_3_7 ===");
        Task_3_7.run(sc, args);

        System.out.println("=== Task_3_8 ===");
        Task_3_8.run(sc);

        System.out.println("=== Task_3_9 ===");
        Task_3_9.run(sc);
    }
}

class Util {
    static long[] nums(Scanner sc) {
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

        return a;
    }

    static int[][] matrix(Scanner sc) {
        System.out.println("Enter n and n*n elements:");

        int n = sc.nextInt();
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = sc.nextInt();
            }
        }

        return m;
    }

    static void print(int[][] m) {
        for (int[] row : m) {
            String o = "";
            for (int v : row) {
                o += v + " ";
            }
            System.out.println(o.trim());
        }
    }
}

class Task_3_4 {
    static int len(long x) {
        return String.valueOf(Math.abs(x)).length();
    }

    static void run(Scanner sc) {
        long[] a = Util.nums(sc);

        int min = Integer.MAX_VALUE;
        for (long x : a) {
            min = Math.min(min, len(x));
        }

        String o = "";
        for (long x : a) {
            if (len(x) == min) {
                o += x + " ";
            }
        }

        System.out.println(o.trim());
    }
}

class Task_3_5 {
    static void run(Scanner sc) {
        long[] a = Util.nums(sc);
        int found = 0;
        long first = 0;

        for (long x : a) {
            String s = String.valueOf(x);
            String rev = new StringBuilder(s).reverse().toString();

            if (x >= 0 && s.equals(rev)) {
                found++;
                if (found == 1) {
                    first = x;
                } else {
                    System.out.println(x);
                    return;
                }
            }
        }

        if (found == 1) {
            System.out.println(first);
        }
    }
}

class Task_3_6 {
    static void run(Scanner sc) {
        System.out.println("Enter x,y,m per line, end with empty line:");

        double x = 0;
        double y = 0;
        double m = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }

            String[] t = line.split(",");
            double w = Double.parseDouble(t[2]);

            x += Double.parseDouble(t[0]) * w;
            y += Double.parseDouble(t[1]) * w;
            m += w;
        }

        System.out.println(x / m + " " + y / m + " " + m);
    }
}

class Task_3_7 {
    static void run(Scanner sc, String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println("Enter " + n + "x" + n + " matrix of 0/1:");

        int[] r = new int[n];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int bit = sc.nextInt();
                r[i] ^= bit;
                c[j] ^= bit;
            }
        }

        int br = 0, bc = 0, bi = 0, bj = 0;
        for (int i = 0; i < n; i++) {
            br += r[i];
            bc += c[i];
            if (r[i] == 1) {
                bi = i;
            }
            if (c[i] == 1) {
                bj = i;
            }
        }

        if (br == 0 && bc == 0) {
            System.out.println("Matrix has parity property");
        } else if (br == 1 && bc == 1) {
            System.out.println("Flip bit (" + bi + ", " + bj + ")");
        } else {
            System.out.println("Matrix is corrupted");
        }
    }
}

class Task_3_8 {
    static int[][] sortRows(int[][] m) {
        int[][] r = new int[m.length][m.length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                r[i][j] = m[i][j];
            }

            Arrays.sort(r[i]);
        }

        return r;
    }

    static int[][] transpose(int[][] m) {
        int[][] t = new int[m.length][m.length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                t[j][i] = m[i][j];
            }
        }

        return t;
    }

    static void run(Scanner sc) {
        int[][] m = Util.matrix(sc);

        System.out.println("Sorted rows:");
        Util.print(sortRows(m));

        System.out.println("Sorted columns:");
        Util.print(transpose(sortRows(transpose(m))));
    }
}

class Task_3_9 {
    static int[][] rotate(int[][] m) {
        int[][] r = new int[m.length][m.length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                r[m.length - 1 - j][i] = m[i][j];
            }
        }

        return r;
    }

    static void run(Scanner sc) {
        int[][] m = Util.matrix(sc);

        System.out.println("Enter angle (90, 180 or 270):");
        int turns = sc.nextInt() / 90 % 4;

        for (int k = 0; k < turns; k++) {
            m = rotate(m);
        }

        Util.print(m);
    }
}
