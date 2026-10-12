package task4_9;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

    private final List<Word> words = new ArrayList<>();

    public Sentence(String text) {
        for (String w : text.split(" ")) {
            words.add(new Word(w));
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Word word : words) {
            result += word + " ";
        }
        return result.trim();
    }
}
