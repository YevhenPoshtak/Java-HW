package task4_9.stone;

/**
 * Amethyst: a semiprecious stone with an additional property, shade.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Amethyst extends SemipreciousStone {

    private static final String NAME = "Amethyst";

    private String shade;

    public Amethyst() {
        this(1, 0, 0);
    }

    public Amethyst(double weight, double pricePerCarat, int transparency) {
        this(weight, pricePerCarat, transparency, "plain", "light purple");
    }

    public Amethyst(double weight, double pricePerCarat, int transparency,
            String pattern, String shade) {
        super(weight, pricePerCarat, transparency, pattern);
        this.shade = shade;
    }

    /** Only the concrete kind of stone knows its name. */
    @Override
    public String getName() {
        return NAME;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && shade.equals(((Amethyst) obj).shade);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + shade.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + ", shade: " + shade;
    }
}
