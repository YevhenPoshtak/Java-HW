package task4_10.bouquet;

import java.util.ArrayList;
import java.util.List;
import task4_10.accessory.Accessory;
import task4_10.flower.Flower;

public class Bouquet {

    private final List<Flower> flowers = new ArrayList<>();
    private final List<Accessory> accessories = new ArrayList<>();

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
    }

    public double getCost() {
        double cost = 0;
        for (Flower flower : flowers) {
            cost += flower.getPrice();
        }
        for (Accessory accessory : accessories) {
            cost += accessory.getPrice();
        }
        return cost;
    }

    public void sortByFreshness() {
        for (int i = 0; i < flowers.size() - 1; i++) {
            for (int j = 0; j < flowers.size() - 1 - i; j++) {
                Flower current = flowers.get(j);
                Flower next = flowers.get(j + 1);
                if (current.getFreshness() < next.getFreshness()) {
                    flowers.set(j, next);
                    flowers.set(j + 1, current);
                }
            }
        }
    }

    public List<Flower> findByStemLength(int from, int to) {
        int min = Math.min(from, to);
        int max = Math.max(from, to);
        List<Flower> found = new ArrayList<>();
        for (Flower flower : flowers) {
            if (flower.getStemLength() >= min
                    && flower.getStemLength() <= max) {
                found.add(flower);
            }
        }
        return found;
    }

    public void print() {
        for (Flower flower : flowers) {
            System.out.println(flower);
        }
        for (Accessory accessory : accessories) {
            System.out.println(accessory);
        }
        System.out.println("Cost: " + getCost());
    }
}
