package task4_11;

public class Main {

    public static void main(String[] args) {
        Publication[] publications = {
            new Handbook("Math Handbook", "Vygodsky"),
            new Encyclopedia("Britannica", "Various authors")
        };
        for (Publication p : publications) {
            p.printInfo();
        }
    }
}
