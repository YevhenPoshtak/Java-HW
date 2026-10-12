package task4_10.accessory;

public class Accessory {

    private final String name;
    private final double price;

    public Accessory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Accessory other = (Accessory) obj;
        return name.equals(other.name)
                && Double.compare(price, other.price) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + Double.hashCode(price);
    }

    @Override
    public String toString() {
        return name + " (price: " + price + ")";
    }
}
