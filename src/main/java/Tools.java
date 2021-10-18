import java.util.ArrayList;
import java.util.List;

public class Tools {
    static boolean isComplet(Automate automate) {
        boolean contains;
        for (char c : automate.getA()) {
            for (Etat e : automate.getEtats()) {
                contains = e.getTransitions() == null || e.getTransitions().stream().anyMatch(transition -> transition.value == c);
                if (!contains) {
                    return false;
                }
            }
        }
        return true;
    }

    static void makeComplete(Automate automate) {
        boolean contains;
        automate.getEtats().add(new Etat("p"));
        for (char c : automate.getA()) {
            for (Etat e : automate.getEtats()) {
                contains = e.getTransitions() == null || e.getTransitions().stream().anyMatch(transition -> transition.value == c);
                if (!contains) {
                    e.getTransitions().add(new Transition(c, e, new Etat("p")));
                }
            }
        }
    }

    static boolean isStandard(Automate automate) {
        if (automate.getI().size() > 1)
            return false;
        for (String initial : automate.getI()) {
            if (automate.getEtats().stream().flatMap(e -> e.getTransitions().stream()).anyMatch(t -> t.arrivee.getNom().equals(initial)))
                return false;
        }
        return true;
    }

    static void makeStandard(Automate automate) {
        Etat tempEtat = new Etat("i", true, false, new ArrayList<>());
        for (Etat e : automate.getEtats()) {
            if (automate.getI().stream().anyMatch(s -> s.equals(e.getNom()))) {
                tempEtat.getTransitions().addAll(e.getTransitions());
            }
        }
        automate.getEtats().add(tempEtat);
    }


}
