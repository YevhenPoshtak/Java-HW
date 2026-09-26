public class Ramanujan {
    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        int count = 0;

        for (long a = 1; a*a*a <= n; a++)
            for (long b = a + 1; a*a*a + b*b*b <= n; b++)
                for (long c = a + 1; 2*c*c*c < a*a*a + b*b*b; c++) {
                    long d = Math.round(Math.cbrt(a*a*a + b*b*b - c*c*c));
                    if (d > c && c*c*c + d*d*d == a*a*a + b*b*b) count++;
                }

        long[] sums = new long[count];
        String[] lines = new String[count];
        int i = 0;

        for (long a = 1; a*a*a <= n; a++)
            for (long b = a + 1; a*a*a + b*b*b <= n; b++) {
                long sum = a*a*a + b*b*b;
                for (long c = a + 1; 2*c*c*c < sum; c++) {
                    long d = Math.round(Math.cbrt(sum - c*c*c));
                    if (d > c && c*c*c + d*d*d == sum) {
                        sums[i] = sum;
                        lines[i] = sum + " = " + a + "^3 + " + b + "^3 = " + c + "^3 + " + d + "^3";
                        i++;
                    }
                }
            }

        for (int x = 1; x < count; x++) {
            long key = sums[x];
            String line = lines[x];
            int j = x - 1;
            while (j >= 0 && sums[j] > key) {
                sums[j + 1] = sums[j];
                lines[j + 1] = lines[j];
                j--;
            }
            sums[j + 1] = key;
            lines[j + 1] = line;
        }

        for (String line : lines) System.out.println(line);

        System.out.println();
        System.out.println("87539319:");
        int ways = countWays(87539319);
        System.out.println(ways + " ways - its the smallest number that is a sum of two cubes in 3 different ways");
    }

    static int countWays(long x) {
        int count = 0;
        for (long a = 1; 2*a*a*a <= x; a++) {
            long b = Math.round(Math.cbrt(x - a*a*a));
            if (a*a*a + b*b*b == x) {
                System.out.println(x + " = " + a + "^3 + " + b + "^3");
                count++;
            }
        }
        return count;
    }
}