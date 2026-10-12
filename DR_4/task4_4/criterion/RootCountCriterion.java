package task4_4.criterion;

import task4_4.equation.QuadraticEquation;

/**
 * Selection by the number of real roots.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class RootCountCriterion implements Criterion {

    private int rootCount;

    public RootCountCriterion() {
        this(2);
    }

    public RootCountCriterion(int rootCount) {
        this.rootCount = rootCount;
    }

    public RootCountCriterion(RootCountCriterion other) {
        this(other.rootCount);
    }

    public int getRootCount() {
        return rootCount;
    }

    public void setRootCount(int rootCount) {
        this.rootCount = rootCount;
    }

    /**
     * Compares the number of roots of the equation with the value
     * stored in this criterion.
     */
    @Override
    public boolean matches(QuadraticEquation equation) {
        return equation.getRoots().length == rootCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return rootCount == ((RootCountCriterion) obj).rootCount;
    }

    @Override
    public int hashCode() {
        return rootCount;
    }

    @Override
    public String toString() {
        return "number of real roots = " + rootCount;
    }
}
