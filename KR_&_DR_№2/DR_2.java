public class DR_2 {
    public static void main(String[] args) {
        int number = (int) (Math.random() * 8);

        int face;
        if (number == 0) {
            face = 1;
        } else if (number == 1) {
            face = 2;
        } else if (number == 2) {
            face = 3;
        } else if (number == 3) {
            face = 4;
        } else if (number == 4) {
            face = 5;
        } else {
            face = 6;
        }

        System.out.println(face);
    }
}