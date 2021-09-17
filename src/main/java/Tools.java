import java.util.List;
import java.util.stream.Collectors;

public class Tools {
    static boolean isComplet(Automate automate, List<Character> alphabet) {
        boolean contains;
        for (char c : alphabet) {
            for (Etat e : automate.getEtats()) {
                contains = e.getTransitions().stream().anyMatch(transition -> transition.value == c);
                if (!contains) {
                    return false;
                }
            }
        }
        return true;
    }

}
