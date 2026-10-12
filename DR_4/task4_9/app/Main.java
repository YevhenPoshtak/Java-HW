package task4_9.app;

import java.util.List;
import java.util.Scanner;
import task4_9.necklace.Necklace;
import task4_9.stock.StoneStock;
import task4_9.stone.Stone;

/**
 * Console menu. The class is responsible only for the dialogue
 * with the user: it reads input and prints results, while all
 * calculations are delegated to Necklace and StoneStock.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Main {

    private StoneStock stock;
    private Necklace necklace;

    public Main() {
        this(new StoneStock());
    }

    /** Selects every second stone of the stock for the necklace. */
    public Main(StoneStock stock) {
        this(stock, new Necklace());
        for (int i = 2; i < stock.size(); i += 2) {
            necklace.addStone(stock.getStone(i));
        }
    }

    public Main(StoneStock stock, Necklace necklace) {
        this.stock = stock;
        this.necklace = necklace;
    }

    /**
     * Entry point: belongs to the application class, it only starts the
     * menu.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public StoneStock getStock() {
        return stock;
    }

    public void setStock(StoneStock stock) {
        this.stock = stock;
    }

    public Necklace getNecklace() {
        return necklace;
    }

    public void setNecklace(Necklace necklace) {
        this.necklace = necklace;
    }

    /**
     * Menu loop. Parsing the user's choice is interface logic, so it
     * lives here and not in the data classes.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1 - Show necklace (weight, cost), "
                    + "2 - Sort by value, "
                    + "3 - Find by transparency, "
                    + "4 - Find in stock by category, 0 - Exit");
            choice = readInt(scanner);
            switch (choice) {
                case 1:
                    System.out.println(necklace);
                    break;
                case 2:
                    necklace.sortByValue();
                    System.out.println(necklace);
                    break;
                case 3:
                    System.out.print("Min and max transparency (0-"
                            + Stone.MAX_TRANSPARENCY + "): ");
                    printFound(necklace.findByTransparency(
                            readInt(scanner), readInt(scanner)));
                    break;
                case 4:
                    System.out.print("Category (precious or "
                            + "semiprecious): ");
                    if (!scanner.hasNext()) {
                        System.exit(0);
                    }
                    printFound(stock.findByCategory(scanner.next()));
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
        Main other = (Main) obj;
        return stock.equals(other.stock) && necklace.equals(other.necklace);
    }

    @Override
    public int hashCode() {
        return 31 * stock.hashCode() + necklace.hashCode();
    }

    @Override
    public String toString() {
        return "Console menu: stock of " + stock.size()
                + " stones, necklace of " + necklace.size() + " stones";
    }

    /**
     * Prints the found stones. Static because it does not use the menu
     * state; the search is done by Necklace and StoneStock.
     */
    private static void printFound(List<Stone> found) {
        if (found.isEmpty()) {
            System.out.println("Stones not found");
        }
        for (Stone stone : found) {
            System.out.println(stone);
        }
    }

    /** Reads a number, asking again on bad input: part of input handling. */
    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            if (!scanner.hasNext()) {
                System.exit(0);
            }
            scanner.next();
            System.out.println("Please enter a number");
        }
        return scanner.nextInt();
    }
}
