import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Automate automate = new Automate();
        Automate automateComplementaire = new Automate();

        try {
            automate.readFromFile("automate.txt");
            automateComplementaire.readFromFile("automate.txt");
            automate.trier();
            automateComplementaire.trier();
            automate.display();
            
            // Langage complementaire sur l'automate lu a l'etape 1
            if (!automateComplementaire.isComplet()) {
            	automateComplementaire.completer();
            }
            
            System.out.println("\nPassage au langage complementaire:");
        	automateComplementaire.langageComplementaire();
        	automateComplementaire.display();

            if (!automate.isDeterministe()) {
                System.out.println("\nL'automate deterministe:");
                automate.determiniser();
                automate.display();
            }

            if (!automate.isComplet()) {
                System.out.println("\nL'automate complet:");
                automate.completer();
                automate.display();
            }

            String mot = "bc";		// "aabbabbaaaaaabbbbbabbbbbbabab"
            boolean isRecognized = false;
            for (Etat etat : automate.getI()) {
                List<Transition> transitions = automate.getTransitions(etat);
                if (transitions != null) {
                    // if the very first letter isn't the same as the first transition symbol, the word won't be recognized
                    isRecognized = transitions.stream().anyMatch(t -> t.getSymbole().equals(String.valueOf(mot.charAt(0)))) && automate.reconnaitLeMot(transitions, mot);
                }
            }
            System.out.println("Le mot '" + mot + "' " + (isRecognized ? "est bien" : "n'est pas") + " reconnu " + (isRecognized ? "v" : "x"));

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
