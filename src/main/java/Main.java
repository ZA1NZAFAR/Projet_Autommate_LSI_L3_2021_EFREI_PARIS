import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile("automate.txt");
            automate.trier();
            automate.display();
            
            if (!automate.isStandard()) {
            	System.out.println("\nL'automate standard:");
            	automate.standardiser();
            	automate.display();
            }
            
            System.out.println("\nL'elimination du mot vide:");
            automate.eliminerMotVide();
            automate.display();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
