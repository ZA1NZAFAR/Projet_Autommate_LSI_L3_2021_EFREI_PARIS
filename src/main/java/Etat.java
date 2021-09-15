import lombok.Data;

import java.util.List;

@Data
public class Etat {
    private char val;
    boolean isInitial;
    boolean isTerminal;
    List<Transition> transitions;
}
