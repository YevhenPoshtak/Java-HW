package task4_9.stone;

/**
 * Agate: a semiprecious stone with an additional property, the
 * number of bands.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Agate extends SemipreciousStone {

    private static final String NAME = "Agate";

    private int bandCount;

    public Agate() {
        this(1, 0, 0);
    }

    public Agate(double weight, double pricePerCarat, int transparency) {
        this(weight, pricePerCarat, transparency, "banded", 0);
    }

    public Agate(double weight, double pricePerCarat, int transparency,
            String pattern, int bandCount) {
        super(weight, pricePerCarat, transparency, pattern);
        this.bandCount = bandCount;
    }

    /** Only the concrete kind of stone knows its name. */
    @Override
    public String getName() {
        return NAME;
    }

    public int getBandCount() {
        return bandCount;
    }

    public void setBandCount(int bandCount) {
        this.bandCount = bandCount;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && bandCount == ((Agate) obj).bandCount;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + bandCount;
    }

    @Override
    public String toString() {
        return super.toString() + ", bands: " + bandCount;
    }
}
