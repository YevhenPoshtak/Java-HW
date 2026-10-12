package task4_4.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import task4_4.criterion.Criterion;
import task4_4.equation.QuadraticEquation;

/**
 * List of equations. The class owns the collection, so operations
 * over all equations at once (largest and smallest root, selection
 * by a criterion) belong to it, not to one equation or the menu.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class EquationList {

    private List<QuadraticEquation> equations;

    public EquationList() {
        this(
                new QuadraticEquation(1, -3, 2),
                new QuadraticEquation(-2, 4, 6),
                new QuadraticEquation(1, 2, 1),
                new QuadraticEquation(1, 0, 4),
                new QuadraticEquation());
    }

    public EquationList(QuadraticEquation... equations) {
        this(Arrays.asList(equations));
    }

    public EquationList(List<QuadraticEquation> equations) {
        this.equations = new ArrayList<>(equations);
    }

    public List<QuadraticEquation> getEquations() {
        return new ArrayList<>(equations);
    }

    public void setEquations(List<QuadraticEquation> equations) {
        this.equations = new ArrayList<>(equations);
    }

    /** Adding changes the collection owned by this class. */
    public void add(QuadraticEquation equation) {
        equations.add(equation);
    }

    /** The number of equations is a property of the collection. */
    public int size() {
        return equations.size();
    }

    /**
     * Largest root among all equations: it needs the whole collection,
     * so the method is here. NaN if there are no real roots.
     */
    public double getMaxRoot() {
        double max = Double.NaN;
        for (QuadraticEquation equation : equations) {
            for (double root : equation.getRoots()) {
                if (Double.isNaN(max) || root > max) {
                    max = root;
                }
            }
        }
        return max;
    }

    /**
     * Smallest root among all equations: it needs the whole collection,
     * so the method is here. NaN if there are no real roots.
     */
    public double getMinRoot() {
        double min = Double.NaN;
        for (QuadraticEquation equation : equations) {
            for (double root : equation.getRoots()) {
                if (Double.isNaN(min) || root < min) {
                    min = root;
                }
            }
        }
        return min;
    }

    /**
     * Selection by a criterion: the list iterates over its elements,
     * the rule itself is given by the passed Criterion.
     */
    public List<QuadraticEquation> find(Criterion criterion) {
        List<QuadraticEquation> found = new ArrayList<>();
        for (QuadraticEquation equation : equations) {
            if (criterion.matches(equation)) {
                found.add(equation);
            }
        }
        return found;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return equations.equals(((EquationList) obj).equations);
    }

    @Override
    public int hashCode() {
        return equations.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < equations.size(); i++) {
            builder.append(i + 1).append(") ").append(equations.get(i))
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
