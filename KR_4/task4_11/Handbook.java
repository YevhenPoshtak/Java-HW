package task4_11;

public class Handbook extends Book {

    public Handbook(String title, String author) {
        super(title, author);
    }

    @Override
    public void printInfo() {
        System.out.println("Handbook: " + getTitle() + ", "
                + getAuthor());
    }
}
