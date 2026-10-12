package task4_4.equation;

/**
 * Quadratic equation ax^2 + bx + c = 0. The class stores the
 * coefficients, so every calculation that depends only on them
 * (roots, extremum, monotonicity intervals) belongs to it.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class QuadraticEquation {

    private double a;
    private double b;
    private double c;

    public QuadraticEquation() {
        this(1, 0, 0);
    }

    public QuadraticEquation(double a, double b, double c) {
        setA(a);
        this.b = b;
        this.c = c;
    }

    public QuadraticEquation(QuadraticEquation other) {
        this(other.a, other.b, other.c);
    }

    public double getA() {
        return a;
    }

    /**
     * The a != 0 check is here because the equation itself is
     * responsible for staying quadratic.
     */
    public final void setA(double a) {
        if (a == 0) {
            throw new IllegalArgumentException(
                    "Coefficient a must not be zero");
        }
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    /** The value at point x depends only on the coefficients. */
    public double getValue(double x) {
        return a * x * x + b * x + c;
    }

    /** The discriminant is a property of the equation, needed for roots. */
    public double getDiscriminant() {
        return b * b - 4 * a * c;
    }

    /**
     * Roots are defined only by the coefficients, so the equation
     * finds them itself; returned in ascending order.
     */
    public double[] getRoots() {
        double discriminant = getDiscriminant();
        if (discriminant < 0) {
            return new double[0];
        }
        if (discriminant == 0) {
            return new double[] {getExtremumX()};
        }
        double sqrt = Math.sqrt(discriminant);
        double first = (-b - sqrt) / (2 * a) + 0.0;
        double second = (-b + sqrt) / (2 * a) + 0.0;
        return new double[] {
            Math.min(first, second), Math.max(first, second)
        };
    }

    /** The vertex abscissa is a property of this equation. */
    public double getExtremumX() {
        return -b / (2 * a) + 0.0;
    }

    /** Function value at the extremum point. */
    public double getExtremumValue() {
        return getValue(getExtremumX()) + 0.0;
    }

    /** The kind of extremum is defined by the sign of coefficient a. */
    public boolean isMinimum() {
        return a > 0;
    }

    /**
     * The increasing interval depends on the vertex and the sign of a,
     * that is, on the data of the equation.
     */
    public Interval getIncreasingInterval() {
        return isMinimum()
                ? new Interval(getExtremumX(), Double.POSITIVE_INFINITY)
                : new Interval(Double.NEGATIVE_INFINITY, getExtremumX());
    }

    /**
     * The decreasing interval depends on the vertex and the sign of a,
     * that is, on the data of the equation.
     */
    public Interval getDecreasingInterval() {
        return isMinimum()
                ? new Interval(Double.NEGATIVE_INFINITY, getExtremumX())
                : new Interval(getExtremumX(), Double.POSITIVE_INFINITY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QuadraticEquation other = (QuadraticEquation) obj;
        return Double.compare(a, other.a) == 0
                && Double.compare(b, other.b) == 0
                && Double.compare(c, other.c) == 0;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(a);
        result = 31 * result + Double.hashCode(b);
        result = 31 * result + Double.hashCode(c);
        return result;
    }

    @Override
    public String toString() {
        return a + "x^2 " + sign(b) + " " + Math.abs(b) + "x "
                + sign(c) + " " + Math.abs(c) + " = 0";
    }

    /**
     * Helper for toString(); static because it does not depend on the
     * object state.
     */
    private static String sign(double value) {
        return value < 0 ? "-" : "+";
    }
}
