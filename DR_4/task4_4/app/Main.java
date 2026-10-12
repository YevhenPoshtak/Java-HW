package task4_4.app;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import task4_4.collection.EquationList;
import task4_4.criterion.Criterion;
import task4_4.criterion.ExtremumCriterion;
import task4_4.criterion.RootCountCriterion;
import task4_4.equation.QuadraticEquation;

/**
 * Console menu. The class is responsible only for the dialogue
 * with the user: it reads input and prints results, while all
 * calculations are delegated to EquationList and QuadraticEquation.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Main {

    private EquationList list;

    public Main() {
        this(new EquationList());
    }

    public Main(EquationList list) {
        this.list = list;
    }

    /**
     * Entry point: belongs to the application class, it only starts the
     * menu.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public EquationList getList() {
        return list;
    }

    public void setList(EquationList list) {
        this.list = list;
    }

    /**
     * Menu loop. Parsing the user's choice is interface logic, so it
     * lives here and not in the data classes.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1 - Roots, extremums, intervals, "
                    + "2 - Largest and smallest root, "
                    + "3 - Find by number of roots, "
                    + "4 - Find by extremum kind, 0 - Exit");
            choice = (int) readNumber(scanner);
            switch (choice) {
                case 1:
                    for (QuadraticEquation equation : list.getEquations()) {
                        printDetails(equation);
                    }
                    break;
                case 2:
                    printExtremeRoots();
                    break;
                case 3:
                    System.out.print("Number of real roots (0, 1 or 2): ");
                    printFound(new RootCountCriterion(
                            (int) readNumber(scanner)));
                    break;
                case 4:
                    System.out.print("Extremum (1 - minimum, "
                            + "2 - maximum): ");
                    printFound(new ExtremumCriterion(
                            (int) readNumber(scanner) == 1));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Unknown option");
            }
        } while (choice != 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return list.equals(((Main) obj).list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return "Console menu for " + list.size() + " equations";
    }

    /**
     * Prints the largest and smallest root. The values are computed by
     * EquationList; this method only outputs them.
     */
    private void printExtremeRoots() {
        if (Double.isNaN(list.getMaxRoot())) {
            System.out.println("No real roots");
            return;
        }
        System.out.println("Largest root: " + list.getMaxRoot());
        System.out.println("Smallest root: " + list.getMinRoot());
    }

    /**
     * Prints the equations selected by a criterion. The selection is
     * done by EquationList; formatting the output is the menu's job.
     */
    private void printFound(Criterion criterion) {
        System.out.println("Criterion: " + criterion);
        List<QuadraticEquation> found = list.find(criterion);
        if (found.isEmpty()) {
            System.out.println("Equations not found");
        }
        for (QuadraticEquation equation : found) {
            System.out.println(equation + ", roots: "
                    + Arrays.toString(equation.getRoots()));
        }
    }

    /**
     * Prints roots, extremum and intervals of one equation. Static
     * because it does not use the menu state; it is not placed in
     * QuadraticEquation so the data class stays console-independent.
     */
    private static void printDetails(QuadraticEquation equation) {
        double[] roots = equation.getRoots();
        System.out.println(equation);
        System.out.println("  roots: " + (roots.length == 0
                ? "no real roots" : Arrays.toString(roots)));
        System.out.println("  "
                + (equation.isMinimum() ? "minimum" : "maximum")
                + ": x = " + equation.getExtremumX()
                + ", y = " + equation.getExtremumValue());
        System.out.println("  increases on "
                + equation.getIncreasingInterval()
                + ", decreases on " + equation.getDecreasingInterval());
    }

    /** Reads a number, asking again on bad input: part of input handling. */
    private static double readNumber(Scanner scanner) {
        while (true) {
            if (!scanner.hasNext()) {
                System.exit(0);
            }
            try {
                return Double.parseDouble(
                        scanner.next().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }
}
