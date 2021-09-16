import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile("automate.txt");
            automate.trier();
            automate.display();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
