package task4_11;

public class Encyclopedia extends Book {

    public Encyclopedia(String title, String author) {
        super(title, author);
    }

    @Override
    public void printInfo() {
        System.out.println("Encyclopedia: " + getTitle() + ", "
                + getAuthor());
    }
}
