package task4_9.stone;

/**
 * Diamond: a precious stone with an additional property, clarity.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Diamond extends PreciousStone {

    private static final String NAME = "Diamond";

    private String clarity;

    public Diamond() {
        this(1, 0, 0);
    }

    public Diamond(double weight, double pricePerCarat, int transparency) {
        this(weight, pricePerCarat, transparency, "round", "VS1");
    }

    public Diamond(double weight, double pricePerCarat, int transparency,
            String cut, String clarity) {
        super(weight, pricePerCarat, transparency, cut);
        this.clarity = clarity;
    }

    /** Only the concrete kind of stone knows its name. */
    @Override
    public String getName() {
        return NAME;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && clarity.equals(((Diamond) obj).clarity);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + clarity.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", clarity: " + clarity;
    }
}
