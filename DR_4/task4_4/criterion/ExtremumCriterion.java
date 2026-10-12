package task4_4.criterion;

import task4_4.equation.QuadraticEquation;

/**
 * Selection by the kind of extremum (minimum or maximum).
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class ExtremumCriterion implements Criterion {

    private boolean minimum;

    public ExtremumCriterion() {
        this(true);
    }

    public ExtremumCriterion(boolean minimum) {
        this.minimum = minimum;
    }

    public ExtremumCriterion(ExtremumCriterion other) {
        this(other.minimum);
    }

    public boolean isMinimum() {
        return minimum;
    }

    public void setMinimum(boolean minimum) {
        this.minimum = minimum;
    }

    /**
     * Compares the extremum kind of the equation with the value stored
     * in this criterion.
     */
    @Override
    public boolean matches(QuadraticEquation equation) {
        return equation.isMinimum() == minimum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return minimum == ((ExtremumCriterion) obj).minimum;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(minimum);
    }

    @Override
    public String toString() {
        return "extremum is a " + (minimum ? "minimum" : "maximum");
    }
}
