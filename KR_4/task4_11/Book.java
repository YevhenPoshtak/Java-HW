package task4_11;

public abstract class Book implements Publication {

    private final String title;
    private final String author;

    protected Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
