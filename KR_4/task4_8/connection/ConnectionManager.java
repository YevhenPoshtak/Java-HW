package task4_8.connection;

public class ConnectionManager {

    private static final Connection[] connections = {
        new Connection(1), new Connection(2), new Connection(3)
    };
    private static int index = 0;

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        if (index == connections.length) {
            System.out.println(
                    "No more connections. ConnectionManager stops working.");
            System.exit(0);
        }
        return connections[index++];
    }

    public static final class Connection {

        private final int id;

        private Connection(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Connection " + id;
        }
    }
}
