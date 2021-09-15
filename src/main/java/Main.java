import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
