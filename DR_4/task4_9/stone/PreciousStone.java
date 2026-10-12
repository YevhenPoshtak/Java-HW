package task4_9.stone;

/**
 * Precious stone. Inheritance from Stone is justified: it is a stone
 * with an additional property, the cut.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public abstract class PreciousStone extends Stone {

    private static final String DEFAULT_CUT = "round";

    private String cut;

    protected PreciousStone() {
        super();
        this.cut = DEFAULT_CUT;
    }

    protected PreciousStone(double weight, double pricePerCarat,
            int transparency) {
        this(weight, pricePerCarat, transparency, DEFAULT_CUT);
    }

    protected PreciousStone(double weight, double pricePerCarat,
            int transparency, String cut) {
        super(weight, pricePerCarat, transparency);
        this.cut = cut;
    }

    /**
     * The category is common to all precious stones, so it is defined
     * here and not in every subclass.
     */
    @Override
    public String getCategory() {
        return "precious";
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && cut.equals(((PreciousStone) obj).cut);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + cut.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", cut: " + cut;
    }
}
