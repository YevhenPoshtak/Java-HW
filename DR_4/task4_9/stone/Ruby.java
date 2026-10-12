package task4_9.stone;

/**
 * Ruby: a precious stone with a heat treatment flag.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Ruby extends PreciousStone {

    private static final String NAME = "Ruby";

    private boolean heatTreated;

    public Ruby() {
        this(1, 0, 0);
    }

    public Ruby(double weight, double pricePerCarat, int transparency) {
        this(weight, pricePerCarat, transparency, "round", false);
    }

    public Ruby(double weight, double pricePerCarat, int transparency,
            String cut, boolean heatTreated) {
        super(weight, pricePerCarat, transparency, cut);
        this.heatTreated = heatTreated;
    }

    /** Only the concrete kind of stone knows its name. */
    @Override
    public String getName() {
        return NAME;
    }

    public boolean isHeatTreated() {
        return heatTreated;
    }

    public void setHeatTreated(boolean heatTreated) {
        this.heatTreated = heatTreated;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && heatTreated == ((Ruby) obj).heatTreated;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Boolean.hashCode(heatTreated);
    }

    @Override
    public String toString() {
        return super.toString()
                + (heatTreated ? ", heat treated" : ", untreated");
    }
}
