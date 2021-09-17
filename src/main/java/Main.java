import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile("automate.txt");
            automate.display();
            System.out.println("Automate Complet = " + Tools.isComplet(automate, new ArrayList<>(automate.getA())));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
