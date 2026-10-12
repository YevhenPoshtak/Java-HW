package task4_9;

public class Main {

    public static void main(String[] args) {
        Text text = new Text(new Sentence("The Little Bun"));
        text.add(new Sentence(
                "Once upon a time there lived an old man and an old woman."));
        text.add(new Sentence("The old woman baked a little bun."));
        text.add(new Sentence("The bun rolled away."));

        System.out.print("Title: ");
        text.printTitle();
        text.print();
    }
}
