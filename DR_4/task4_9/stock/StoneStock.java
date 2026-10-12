package task4_9.stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import task4_9.stone.Agate;
import task4_9.stone.Amethyst;
import task4_9.stone.Diamond;
import task4_9.stone.Ruby;
import task4_9.stone.Stone;

/**
 * Stone stock: the class that creates the array of objects.
 * Operations on the array (access by index, selection by category)
 * belong to it.
 * Accessors, equals(), hashCode() and toString() belong here
 * because they work only with the fields of this class.
 */
public class StoneStock {

    private Stone[] stones;

    public StoneStock() {
        this(
                new Diamond(1.5, 5000, 95, "brilliant", "VVS1"),
                new Diamond(0.8, 4200, 90),
                new Ruby(2, 3000, 70, "oval", true),
                new Ruby(1.2, 3500, 60),
                new Amethyst(4, 60, 75),
                new Amethyst(2.5, 80, 85, "plain", "deep purple"),
                new Agate(10, 5, 20, "banded", 12),
                new Agate(6, 8, 5));
    }

    public StoneStock(Stone... stones) {
        this.stones = stones.clone();
    }

    public StoneStock(StoneStock other) {
        this(other.stones);
    }

    public Stone[] getStones() {
        return stones.clone();
    }

    public void setStones(Stone[] stones) {
        this.stones = stones.clone();
    }

    /** The number of stones is a property of the array the stock owns. */
    public int size() {
        return stones.length;
    }

    /** Access to a stone by index without exposing the array itself. */
    public Stone getStone(int index) {
        return stones[index];
    }

    /** Data selection criterion: stones of the stock by category. */
    public List<Stone> findByCategory(String category) {
        List<Stone> found = new ArrayList<>();
        for (Stone stone : stones) {
            if (stone.getCategory().equalsIgnoreCase(category)) {
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
        return Arrays.equals(stones, ((StoneStock) obj).stones);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(stones);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stones.length; i++) {
            builder.append(i + 1).append(") ").append(stones[i])
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
