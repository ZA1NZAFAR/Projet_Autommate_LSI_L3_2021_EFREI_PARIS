import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        try {
            automate.readFromFile("automate.txt");
            automate.trier();
            automate.display();
            
            String mot = "aabbabbaaaaaabbbbbabbbbbbabab";
            for (Etat etat : automate.getI()) {
            	List<Transition> transitions = automate.getTransitions(etat);
            	if (transitions != null) {
            		boolean isRecognized =  automate.reconnaitLeMot(transitions, mot);
            		System.out.println("Le mot '" + mot + "' " + (isRecognized ? "est bien" : "n'est pas") + " reconnu " + (isRecognized ?  "✔️" : "❌"));
            	}
            }
            
            if (!automate.isStandard()) {
            	System.out.println("\nL'automate standard:");
            	automate.standardiser();
            	automate.display();
            }
            
            for (Etat etat : automate.getI()) {
            	if (automate.getT().contains(etat)) {
            		System.out.println("\nL'elimination du mot vide:");
                    automate.eliminerMotVide();
                    automate.display();
                    break;
            	}
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
