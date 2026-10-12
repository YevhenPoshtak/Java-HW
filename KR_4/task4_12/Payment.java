package task4_12;

import java.util.ArrayList;
import java.util.List;

public class Payment {

    private final List<Product> products = new ArrayList<>();

    public class Product {

        private final String name;
        private final double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public void add(String name, double price) {
        products.add(new Product(name, price));
    }

    public void print() {
        double total = 0;
        for (Product product : products) {
            System.out.println(product.name + " - " + product.price);
            total += product.price;
        }
        System.out.println("Total: " + total);
    }
}
