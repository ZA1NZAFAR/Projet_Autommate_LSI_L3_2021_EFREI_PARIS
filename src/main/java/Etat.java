import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etat {
    int nom;
    boolean isInitial;
    boolean isTerminal;
    List<Transition> transitions;

    Set<Etat> getSuccesseurs(char c) {
        Set<Etat> res = new HashSet<>();
        for (Transition t : transitions)
            if (t.value == c)
                res.add(t.arrivee);
        return res;
    }

    @Override
    public String toString() {
        return "Etat{" +
                "val=" + nom +
                ", isInitial=" + isInitial +
                ", isTerminal=" + isTerminal +
                ", Transitions=" + transitions +
                "}\n";
    }

    List<Etat> getListOfSuccessors(char c) {
        List<Etat> set = new ArrayList<>();
        for (Transition t : transitions) {
            if (t.value == c) {
                set.add(t.arrivee);
            }
        }
        return set;
    }

    boolean hasSuccesseur(Transition transition) {
        for (Transition t : this.transitions) {
            if (transition.value == t.value && transition.arrivee.equals(t.arrivee) && transition.depart.equals(t.depart))
                return true;
        }
        return false;
    }
}
