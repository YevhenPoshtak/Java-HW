package task4_10.flower;

public abstract class Flower {

    public static final int MAX_FRESHNESS = 10;

    private final double price;
    private final int freshness;
    private final int stemLength;

    protected Flower(double price, int freshness, int stemLength) {
        this.price = price;
        this.freshness = freshness;
        this.stemLength = stemLength;
    }

    public abstract String getName();

    public double getPrice() {
        return price;
    }

    public int getFreshness() {
        return freshness;
    }

    public int getStemLength() {
        return stemLength;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Flower other = (Flower) obj;
        return Double.compare(price, other.price) == 0
                && freshness == other.freshness
                && stemLength == other.stemLength;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(price);
        result = 31 * result + freshness;
        result = 31 * result + stemLength;
        return result;
    }

    @Override
    public String toString() {
        return getName() + " (price: " + price + ", freshness: "
                + freshness + "/" + MAX_FRESHNESS + ", stem: "
                + stemLength + " cm)";
    }
}
