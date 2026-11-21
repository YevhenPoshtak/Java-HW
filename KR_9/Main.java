import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, System.getProperty("stdin.encoding", "UTF-8"));

        while (true) {
            System.out.println("\n===== Main menu =====");
            System.out.println("1 - Task_8_6 (messages and a queue)");
            System.out.println("2 - Task_8_7 (call center)");
            System.out.println("3 - Task_8_8 (free cash desk)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            String choice = in.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    Task_8_6.menu(in);
                }
                else if (choice.equals("2")) {
                    Task_8_7.menu(in);
                }
                else if (choice.equals("3")) {
                    Task_8_8.menu(in);
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

    static int[] readInts(Scanner in, String prompt, int count) {
        System.out.print(prompt);
        String[] parts = in.nextLine().trim().split("\\s+");
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = Integer.parseInt(parts[i]);
        }
        return values;
    }

    static int randomBetween(Random random, int from, int to) {
        return from + random.nextInt(to - from + 1);
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class Task_8_6 {

    static final String STOP = "STOP";

    static void menu(Scanner in) throws InterruptedException {
        System.out.println("\n----- Task_8_6 -----");
        int[] v = Main.readInts(in, "Enter t1 (generation, ms), t2 (processing, ms), number of messages: ", 3);
        int t1 = v[0];
        int t2 = v[1];
        int count = v[2];

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= count; i++) {
                    Thread.sleep(t1);
                    System.out.println("Generated: Message " + i);
                    queue.put("Message " + i);
                }
                queue.put(STOP);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String message;
                while (!(message = queue.take()).equals(STOP)) {
                    Thread.sleep(t2);
                    System.out.println("    Processed: " + message);
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}


class Task_8_7 {

    static class Client extends Thread {
        private final int number;
        private final Semaphore operators;
        private final int t3, t4, t5, t6;
        private final Random random = new Random();

        Client(int number, Semaphore operators, int t3, int t4, int t5, int t6) {
            this.number = number;
            this.operators = operators;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
        }

        @Override
        public void run() {
            while (!operators.tryAcquire()) {
                int pause = Main.randomBetween(random, t5, t6);
                System.out.println("Client " + number + ": all operators are busy, hangs up and calls again in " + pause + " ms");
                Main.sleep(pause);
            }
            int service = Main.randomBetween(random, t3, t4);
            System.out.println("Client " + number + ": connected to an operator for " + service + " ms");
            Main.sleep(service);
            System.out.println("Client " + number + ": call finished");
            operators.release();
        }
    }

    static void menu(Scanner in) throws InterruptedException {
        System.out.println("\n----- Task_8_7 -----");
        int[] v = Main.readInts(in, "Enter N (operators) and the number of clients: ", 2);
        int[] t = Main.readInts(in, "Enter t1 t2 t3 t4 t5 t6 (ms): ", 6);

        Semaphore operators = new Semaphore(v[0]);
        Random random = new Random();
        List<Client> clients = new ArrayList<>();

        for (int i = 1; i <= v[1]; i++) {
            Main.sleep(Main.randomBetween(random, t[0], t[1]));
            Client client = new Client(i, operators, t[2], t[3], t[4], t[5]);
            clients.add(client);
            client.start();
        }
        for (Client client : clients) {
            client.join();
        }
        System.out.println("All clients served.");
    }
}


class Task_8_8 {

    static class Restaurant {
        private final List<LinkedList<Visitor>> queues = new ArrayList<>();
        private boolean closed = false;

        Restaurant(int cashDesks) {
            for (int i = 0; i < cashDesks; i++) {
                queues.add(new LinkedList<>());
            }
        }

        synchronized int shortestQueue() {
            int best = 0;
            for (int i = 1; i < queues.size(); i++) {
                if (queues.get(i).size() < queues.get(best).size()) {
                    best = i;
                }
            }
            return best;
        }

        synchronized void join(Visitor visitor, int desk) {
            queues.get(desk).add(visitor);
            visitor.desk = desk;
            System.out.println("Visitor " + visitor.number + " stands in the queue of cash desk " + (desk + 1));
            notifyAll();
        }

        synchronized Visitor next(int desk) throws InterruptedException {
            while (queues.get(desk).isEmpty()) {
                if (closed) {
                    return null;
                }
                wait();
            }
            Visitor visitor = queues.get(desk).removeFirst();
            visitor.served = true;
            notifyAll();
            return visitor;
        }

        synchronized void close() {
            closed = true;
            notifyAll();
        }

        synchronized void waitAndMaybeSwitch(Visitor visitor) throws InterruptedException {
            wait(100);
            if (visitor.served) {
                return;
            }
            LinkedList<Visitor> own = queues.get(visitor.desk);
            int ahead = own.indexOf(visitor);
            int target = shortestQueue();
            if (target != visitor.desk && queues.get(target).size() < ahead) {
                own.remove(visitor);
                System.out.println("Visitor " + visitor.number + " moves from cash desk " + (visitor.desk + 1)
                        + " to cash desk " + (target + 1));
                join(visitor, target);
            }
        }
    }

    static class Visitor extends Thread {
        private final int number;
        private final Restaurant restaurant;
        private final int desk0;
        int desk;
        boolean served = false;

        Visitor(int number, Restaurant restaurant, int desk0) {
            this.number = number;
            this.restaurant = restaurant;
            this.desk0 = desk0;
        }

        @Override
        public void run() {
            restaurant.join(this, desk0);
            try {
                synchronized (restaurant) {
                    while (!served) {
                        restaurant.waitAndMaybeSwitch(this);
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static void menu(Scanner in) throws InterruptedException {
        System.out.println("\n----- Task_8_8 -----");
        int[] v = Main.readInts(in, "Enter the number of cash desks and the number of visitors: ", 2);
        int[] t = Main.readInts(in, "Enter the max arrival pause and the service time (ms): ", 2);

        Restaurant restaurant = new Restaurant(v[0]);
        List<Thread> cashiers = new ArrayList<>();
        for (int i = 0; i < v[0]; i++) {
            int desk = i;
            Thread cashier = new Thread(() -> {
                try {
                    Visitor visitor;
                    while ((visitor = restaurant.next(desk)) != null) {
                        System.out.println("    Cash desk " + (desk + 1) + " serves visitor " + visitor.number);
                        Main.sleep(t[1]);
                        System.out.println("    Cash desk " + (desk + 1) + " finished visitor " + visitor.number);
                    }
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            cashiers.add(cashier);
            cashier.start();
        }

        Random random = new Random();
        List<Visitor> visitors = new ArrayList<>();
        for (int i = 1; i <= v[1]; i++) {
            Main.sleep(random.nextInt(t[0] + 1));
            Visitor visitor = new Visitor(i, restaurant, random.nextInt(v[0]));
            visitors.add(visitor);
            visitor.start();
        }

        for (Visitor visitor : visitors) {
            visitor.join();
        }
        restaurant.close();
        for (Thread cashier : cashiers) {
            cashier.join();
        }
        System.out.println("The restaurant is closed.");
    }
}
