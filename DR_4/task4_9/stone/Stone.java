package task4_9.stone;

/**
 * Stone. Base class of the hierarchy: every stone has weight, price
 * per carat and transparency, so they and their methods are here.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public abstract class Stone {

    public static final int MAX_TRANSPARENCY = 100;

    private double weight;
    private double pricePerCarat;
    private int transparency;

    protected Stone() {
        this(1, 0, 0);
    }

    protected Stone(double weight, double pricePerCarat) {
        this(weight, pricePerCarat, 0);
    }

    protected Stone(double weight, double pricePerCarat, int transparency) {
        setWeight(weight);
        setPricePerCarat(pricePerCarat);
        setTransparency(transparency);
    }

    /**
     * The name depends on the concrete kind of stone, so the method is
     * abstract and implemented in subclasses.
     */
    public abstract String getName();

    /**
     * The category is defined by the intermediate class of the
     * hierarchy (precious or semiprecious).
     */
    public abstract String getCategory();

    public double getWeight() {
        return weight;
    }

    /**
     * The weight check is here because the stone itself is responsible
     * for the validity of its data.
     */
    public final void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
    }

    public double getPricePerCarat() {
        return pricePerCarat;
    }

    /**
     * The price check is here because the stone itself is responsible
     * for the validity of its data.
     */
    public final void setPricePerCarat(double pricePerCarat) {
        if (pricePerCarat < 0) {
            throw new IllegalArgumentException(
                    "Price must not be negative");
        }
        this.pricePerCarat = pricePerCarat;
    }

    public int getTransparency() {
        return transparency;
    }

    /** The transparency range check is here for the same reason. */
    public final void setTransparency(int transparency) {
        if (transparency < 0 || transparency > MAX_TRANSPARENCY) {
            throw new IllegalArgumentException(
                    "Transparency must be from 0 to " + MAX_TRANSPARENCY);
        }
        this.transparency = transparency;
    }

    /** The value of a stone is computed from its own weight and price. */
    public double getValue() {
        return weight * pricePerCarat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Stone other = (Stone) obj;
        return Double.compare(weight, other.weight) == 0
                && Double.compare(pricePerCarat, other.pricePerCarat) == 0
                && transparency == other.transparency;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(weight);
        result = 31 * result + Double.hashCode(pricePerCarat);
        result = 31 * result + transparency;
        return result;
    }

    @Override
    public String toString() {
        return getName() + " [" + getCategory() + "] (weight: " + weight
                + " ct, price per carat: " + pricePerCarat
                + ", value: " + getValue() + ", transparency: "
                + transparency + "/" + MAX_TRANSPARENCY + ")";
    }
}
