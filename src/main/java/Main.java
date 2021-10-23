import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile("automate.txt");

            automate.display();
            System.out.println("Automate Standard = " + Tools.isStandard(automate));
            Tools.makeStandard(automate);
            automate.display();
            System.out.println("Automate Standard = " + Tools.isStandard(automate));


            automate.display();
            System.out.println("Automate Complet = " + Tools.isComplet(automate));
            Tools.makeComplete(automate);
            automate.display();
            System.out.println("Automate Complet = " + Tools.isComplet(automate));


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
