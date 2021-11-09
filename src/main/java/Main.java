import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        
        try {
            automate.readFromFile("automate.txt");
            automate.trier();
            automate.display();
            
//            if (!automate.isDeterministe()) {
//            	System.out.println("\nL'automate déterministe:");
//            	automate.determiniser();
//            	automate.display();
//            }
            
            if (!automate.isComplet()) {
                System.out.println("\nL'automate complet:");
                automate.completer();
                automate.display();
            }
            
            System.out.println("\nPassage au langage complementaire:");
            automate.langageComplementaire();
            automate.display();
            
            String mot = "aabbabbaaaaaabbbbbabbbbbbabab";
            boolean isRecognized = false;
            for (Etat etat : automate.getI()) {
            	List<Transition> transitions = automate.getTransitions(etat);
            	if (transitions != null) {
            		// if the very first letter isn't the same as the first transition symbol, the word won't be recognized
            		isRecognized = (transitions.stream().filter(t -> t.getSymbole().equals(String.valueOf(mot.charAt(0))))
            				.collect(Collectors.toList()).size() == 0) ? false : automate.reconnaitLeMot(transitions, mot);
            	}
            }
            System.out.println("Le mot '" + mot + "' " + (isRecognized ? "est bien" : "n'est pas") + " reconnu " + (isRecognized ?  "✔️" : "❌"));
            
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
            
            for (Etat etat : automate.getI()) {
            	if (!automate.getT().contains(etat)) {
            		System.out.println("\nL'ajout du mot vide:");
            		automate.ajouterMotVide();
            		automate.display();
            		break;
            	}
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
