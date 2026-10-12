package task4_12;

public class Main {

    public static void main(String[] args) {
        Payment payment = new Payment();
        payment.add("Bread", 30);
        payment.add("Milk", 45.5);
        payment.add("Cheese", 120);
        payment.print();
    }
}
