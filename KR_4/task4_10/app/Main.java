package task4_10.app;

import java.util.List;
import java.util.Scanner;
import task4_10.accessory.Accessory;
import task4_10.bouquet.Bouquet;
import task4_10.flower.Chamomile;
import task4_10.flower.Flower;
import task4_10.flower.Rose;
import task4_10.flower.Tulip;

public class Main {

    private static final Flower[] FLOWERS = {
        new Rose(60, 7, 50, true),
        new Tulip(35, 9, 30, "red"),
        new Chamomile(20, 5, 25, 21),
        new Rose(75, 10, 60, false),
        new Tulip(30, 4, 28, "yellow"),
        new Chamomile(18, 8, 22, 34)
    };

    private static final Accessory[] ACCESSORIES = {
        new Accessory("Ribbon", 15),
        new Accessory("Wrapping paper", 25)
    };

    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();
        for (Flower flower : FLOWERS) {
            bouquet.addFlower(flower);
        }
        for (Accessory accessory : ACCESSORIES) {
            bouquet.addAccessory(accessory);
        }

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1 - Show bouquet and cost, "
                    + "2 - Sort by freshness, "
                    + "3 - Find by stem length, 0 - Exit");
            choice = readInt(scanner);
            switch (choice) {
                case 1:
                    bouquet.print();
                    break;
                case 2:
                    bouquet.sortByFreshness();
                    bouquet.print();
                    break;
                case 3:
                    System.out.print("Min and max length: ");
                    printFound(bouquet.findByStemLength(
                            readInt(scanner), readInt(scanner)));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Unknown option");
            }
        } while (choice != 0);
    }

    private static void printFound(List<Flower> found) {
        if (found.isEmpty()) {
            System.out.println("Flowers not found");
        }
        for (Flower flower : found) {
            System.out.println(flower);
        }
    }

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
