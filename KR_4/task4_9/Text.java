package task4_9;

import java.util.ArrayList;
import java.util.List;

public class Text {

    private final Sentence title;
    private final List<Sentence> sentences = new ArrayList<>();

    public Text(Sentence title) {
        this.title = title;
    }

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    public void print() {
        for (Sentence sentence : sentences) {
            System.out.println(sentence);
        }
    }

    public void printTitle() {
        System.out.println(title);
    }

    public Sentence getTitle() {
        return title;
    }
}
