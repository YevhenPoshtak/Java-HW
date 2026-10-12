package task4_9.stone;

/**
 * Semiprecious stone. Inheritance from Stone is justified: it is a
 * stone with an additional property, the pattern.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public abstract class SemipreciousStone extends Stone {

    private static final String DEFAULT_PATTERN = "plain";

    private String pattern;

    protected SemipreciousStone() {
        super();
        this.pattern = DEFAULT_PATTERN;
    }

    protected SemipreciousStone(double weight, double pricePerCarat,
            int transparency) {
        this(weight, pricePerCarat, transparency, DEFAULT_PATTERN);
    }

    protected SemipreciousStone(double weight, double pricePerCarat,
            int transparency, String pattern) {
        super(weight, pricePerCarat, transparency);
        this.pattern = pattern;
    }

    /**
     * The category is common to all semiprecious stones, so it is
     * defined here and not in every subclass.
     */
    @Override
    public String getCategory() {
        return "semiprecious";
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && pattern.equals(((SemipreciousStone) obj).pattern);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + pattern.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", pattern: " + pattern;
    }
}
