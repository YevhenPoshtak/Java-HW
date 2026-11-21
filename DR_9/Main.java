import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));
        Task_8_2.menu(in);
    }
}


class Task_8_2 {

    static class Tunnel {
        private final int number;
        private final int capacity;
        private int direction = 0;
        private int inside = 0;
        private final int[] waiting = new int[3];

        Tunnel(int number, int capacity) {
            this.number = number;
            this.capacity = capacity;
        }

        private boolean canEnter(int dir) {
            if (inside == 0) {
                return true;
            }
            return direction == dir && inside < capacity && waiting[3 - dir] == 0;
        }

        synchronized boolean enter(int dir, long maxWait) throws InterruptedException {
            long deadline = System.currentTimeMillis() + maxWait;
            waiting[dir]++;
            try {
                while (!canEnter(dir)) {
                    long left = deadline - System.currentTimeMillis();
                    if (left <= 0) {
                        notifyAll();
                        return false;
                    }
                    wait(left);
                }
            }
            finally {
                waiting[dir]--;
            }
            direction = dir;
            inside++;
            return true;
        }

        synchronized void leave() {
            inside--;
            if (inside == 0) {
                direction = 0;
            }
            notifyAll();
        }
    }

    static class Train extends Thread {
        private final int number;
        private final int dir;
        private final Tunnel[] tunnels;
        private final int maxWait;
        private final int crossTime;

        Train(int number, int dir, Tunnel[] tunnels, int maxWait, int crossTime) {
            this.number = number;
            this.dir = dir;
            this.tunnels = tunnels;
            this.maxWait = maxWait;
            this.crossTime = crossTime;
        }

        private String name() {
            return "Train " + number + " (" + (dir == 1 ? "A->B" : "B->A") + ")";
        }

        @Override
        public void run() {
            try {
                int current = number % tunnels.length;
                while (!tunnels[current].enter(dir, maxWait)) {
                    int other = (current + 1) % tunnels.length;
                    System.out.println(name() + ": waited too long at tunnel " + tunnels[current].number
                            + ", redirected to tunnel " + tunnels[other].number);
                    current = other;
                }
                System.out.println(name() + ": enters tunnel " + tunnels[current].number);
                Thread.sleep(crossTime);
                System.out.println(name() + ": leaves tunnel " + tunnels[current].number);
                tunnels[current].leave();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static void menu(Scanner in) throws InterruptedException {
        System.out.print("Enter the number of trains, max waiting time (ms), crossing time (ms): ");
        String[] parts = in.nextLine().trim().split("\\s+");
        int trains = Integer.parseInt(parts[0]);
        int maxWait = Integer.parseInt(parts[1]);
        int crossTime = Integer.parseInt(parts[2]);

        Tunnel[] tunnels = {new Tunnel(1, 2), new Tunnel(2, 2)};
        Random random = new Random();
        List<Train> list = new ArrayList<>();

        for (int i = 1; i <= trains; i++) {
            Train train = new Train(i, 1 + random.nextInt(2), tunnels, maxWait, crossTime);
            list.add(train);
            train.start();
            Thread.sleep(random.nextInt(100));
        }
        for (Train train : list) {
            train.join();
        }
        System.out.println("All trains passed.");
    }
}
