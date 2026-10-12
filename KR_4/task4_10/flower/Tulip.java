package task4_10.flower;

public class Tulip extends Flower {

    private final String color;

    public Tulip(double price, int freshness, int stemLength, String color) {
        super(price, freshness, stemLength);
        this.color = color;
    }

    @Override
    public String getName() {
        return "Tulip";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && color.equals(((Tulip) obj).color);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", color: " + color;
    }
}
