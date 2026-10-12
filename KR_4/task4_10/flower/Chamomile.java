package task4_10.flower;

public class Chamomile extends Flower {

    private final int petalCount;

    public Chamomile(double price, int freshness, int stemLength,
            int petalCount) {
        super(price, freshness, stemLength);
        this.petalCount = petalCount;
    }

    @Override
    public String getName() {
        return "Chamomile";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && petalCount == ((Chamomile) obj).petalCount;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + petalCount;
    }

    @Override
    public String toString() {
        return super.toString() + ", petals: " + petalCount;
    }
}
