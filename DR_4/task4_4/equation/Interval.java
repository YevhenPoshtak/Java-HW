package task4_4.equation;

/**
 * Open numeric interval (from; to). A separate class so that
 * monotonicity intervals have their own type instead of two numbers.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Interval {

    private double from;
    private double to;

    public Interval() {
        this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public Interval(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public Interval(Interval other) {
        this(other.from, other.to);
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    /** Whether a point is inside depends only on the interval bounds. */
    public boolean contains(double x) {
        return x > from && x < to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Interval other = (Interval) obj;
        return Double.compare(from, other.from) == 0
                && Double.compare(to, other.to) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(from) + Double.hashCode(to);
    }

    @Override
    public String toString() {
        return "(" + format(from) + "; " + format(to) + ")";
    }

    /**
     * Helper for toString(); static because it does not depend on the
     * object state.
     */
    private static String format(double bound) {
        if (Double.isInfinite(bound)) {
            return bound > 0 ? "+inf" : "-inf";
        }
        return String.valueOf(bound);
    }
}
