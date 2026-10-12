package task4_10.flower;

public class Rose extends Flower {

    private final boolean hasThorns;

    public Rose(double price, int freshness, int stemLength,
            boolean hasThorns) {
        super(price, freshness, stemLength);
        this.hasThorns = hasThorns;
    }

    @Override
    public String getName() {
        return "Rose";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && hasThorns == ((Rose) obj).hasThorns;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Boolean.hashCode(hasThorns);
    }

    @Override
    public String toString() {
        return super.toString()
                + (hasThorns ? ", with thorns" : ", without thorns");
    }
}
