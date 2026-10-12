package task4_9.necklace;

import java.util.ArrayList;
import java.util.List;
import task4_9.stone.Stone;

/**
 * Necklace. The class owns the selected stones, so operations over
 * all of them (total weight, cost, sorting, search) belong to it,
 * not to a single stone or the menu.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class Necklace {

    private List<Stone> stones;

    public Necklace() {
        stones = new ArrayList<>();
    }

    public Necklace(Stone... stones) {
        this();
        for (Stone stone : stones) {
            addStone(stone);
        }
    }

    public Necklace(List<Stone> stones) {
        this();
        for (Stone stone : stones) {
            addStone(stone);
        }
    }

    public List<Stone> getStones() {
        return new ArrayList<>(stones);
    }

    public void setStones(List<Stone> stones) {
        this.stones = new ArrayList<>();
        for (Stone stone : stones) {
            addStone(stone);
        }
    }

    /**
     * Adding changes the necklace contents; the same stone (object)
     * cannot be in the necklace twice.
     */
    public final boolean addStone(Stone stone) {
        if (holds(stone)) {
            return false;
        }
        stones.add(stone);
        return true;
    }

    /**
     * Compares by reference: two stones with equal parameters are
     * different physical stones, so equals() does not fit here.
     */
    private boolean holds(Stone stone) {
        for (Stone current : stones) {
            if (current == stone) {
                return true;
            }
        }
        return false;
    }

    /** The number of stones is a property of the necklace. */
    public int size() {
        return stones.size();
    }

    /** Total weight in carats: the sum over all stones of the necklace. */
    public double getTotalWeight() {
        double weight = 0;
        for (Stone stone : stones) {
            weight += stone.getWeight();
        }
        return weight;
    }

    /** Total cost: the sum of values of all stones of the necklace. */
    public double getTotalCost() {
        double cost = 0;
        for (Stone stone : stones) {
            cost += stone.getValue();
        }
        return cost;
    }

    /**
     * Sorting by descending value changes the order of stones in the
     * necklace itself.
     */
    public void sortByValue() {
        for (int i = 0; i < stones.size() - 1; i++) {
            for (int j = 0; j < stones.size() - 1 - i; j++) {
                Stone current = stones.get(j);
                Stone next = stones.get(j + 1);
                if (current.getValue() < next.getValue()) {
                    stones.set(j, next);
                    stones.set(j + 1, current);
                }
            }
        }
    }

    /** Search by transparency range among the stones of the necklace. */
    public List<Stone> findByTransparency(int from, int to) {
        int min = Math.min(from, to);
        int max = Math.max(from, to);
        List<Stone> found = new ArrayList<>();
        for (Stone stone : stones) {
            if (stone.getTransparency() >= min
                    && stone.getTransparency() <= max) {
                found.add(stone);
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
        return stones.equals(((Necklace) obj).stones);
    }

    @Override
    public int hashCode() {
        return stones.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Stone stone : stones) {
            builder.append(stone).append(System.lineSeparator());
        }
        builder.append("Total weight: ").append(getTotalWeight())
                .append(" ct, total cost: ").append(getTotalCost());
        return builder.toString();
    }
}
