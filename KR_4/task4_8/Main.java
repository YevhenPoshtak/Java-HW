package task4_8;

import task4_8.connection.ConnectionManager;
import task4_8.connection.ConnectionManager.Connection;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Connection connection = ConnectionManager.getConnection();
            System.out.println(connection);
        }
    }
}
