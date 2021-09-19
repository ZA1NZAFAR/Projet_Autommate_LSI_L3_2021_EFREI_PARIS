import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Automate {
    private final LinkedHashSet<String> A = Sets.newLinkedHashSet();
    private final LinkedHashSet<Etat> Q = Sets.newLinkedHashSet();
    private final LinkedHashSet<Etat> I = Sets.newLinkedHashSet();
    private final LinkedHashSet<Etat> T = Sets.newLinkedHashSet();
    private final LinkedHashMap<Etat, List<Transition>> E = Maps.newLinkedHashMap();

    public void readFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String line = br.readLine();
        int alphabetCount = Integer.parseInt(line);
        char character = 'a';

        // Alphabet - A
        for (int i = 0; i < alphabetCount; i++) {
            A.add(String.valueOf(character++));
        }

        // Etats - Q
        line = br.readLine();
        int etatCount = Integer.parseInt(line);

        int etatNom = 1;
        for (int i = 0; i < etatCount; i++) {
        	Etat etat = new Etat(String.valueOf(etatNom++));
            Q.add(etat);
        }

        // Etats initiaux - I
        line = br.readLine();
        String[] values = line.split("\\s");

        for (int i = 1; i <= Integer.parseInt(values[0]); i++) {
        	Etat etat = (Etat) Q.toArray()[Integer.parseInt(values[i]) - 1];
//        	System.out.println(Q.stream().filter(new Etat(values[i])::equals).findAny().orElse(null));
            I.add(etat);
        }

        // Etats terminaux - T
        line = br.readLine();
        values = line.split("\\s");

        for (int i = 1; i <= Integer.parseInt(values[0]); i++) {
        	Etat etat = (Etat) Q.toArray()[Integer.parseInt(values[i]) - 1];
            T.add(etat);
        }

        // Transitions - E
        line = br.readLine();
        do {
            values = line.split("\\s");
            Etat courant = (Etat) Q.toArray()[Integer.parseInt(values[0]) - 1];
            String symbole = values[1];
            Etat cible = (Etat) Q.toArray()[Integer.parseInt(values[2]) - 1];

            Transition transition = new Transition(courant, symbole, cible);

            List<Transition> list = E.get(courant) == null ? new ArrayList<>() : E.get(courant);
            list.add(transition);
            E.put(courant, list);

            line = br.readLine();
        } while (line != null);
        br.close();
    }

    public void display() {
    	String indent = String.format("%-" + (A.size() * (Q.size() * 3)) + "s", "");			// spaces.

        System.out.print("Etats" + indent.substring(0, indent.length() - "Etats".length()));
        for (String a : A) {
            System.out.print(a + indent.substring(0, indent.length() - 1));
        }
        
        System.out.println("");
        for (Map.Entry<Etat, List<Transition>> e : E.entrySet()) {
            System.out.print(e.getKey() + indent.substring(0, indent.length() - 1));
            
            for (String a : A) {
                System.out.print(
                		// Collect to list the transitions that have 'a' as a symbol
                        e.getValue().stream().filter(t -> t.getSymbole().equals(a)).collect(Collectors.toList())
                                .stream()
                                // get their end vertices (etats cibles)
                                .map(transition -> transition.getCible())
//                              // Store them in a list, add indentation for each vertex (etat)
                                .collect(Collectors.toList()) + indent.substring(0, indent.length() - 
                                e.getValue().stream().filter(t -> t.getSymbole().equals(a)).collect(Collectors.toList())
                                .stream().map(transition -> transition.getCible()).collect(Collectors.toList()).toString().length()));
            }
            
            System.out.println();
        }
        
        System.out.println("\nLes etats initiaux : " + I);
        System.out.println("Les etats terminaux : " + T);
        
        String isStandard = isStandard() == true ? "est standard" : "n'est pas standard";
        System.out.println("L'automate " + isStandard);
    }
    
    public boolean isStandard() {
    	if (I.size() != 1)
    		return false;
    	
    	for (List<Transition> transitions : E.values()) {
    		for (Transition transition : transitions) {
    			if (transition.getCible().equals(I.iterator().next()))
    					return false;
    		}
    	}
    	
    	return true;
    }
    
    public void standardiser() {
    	// get list of transition of the first etat initial
    	List<Transition> transitions = new ArrayList<>();
    	
    	for (Etat etat : I) {
    		if (E.containsKey(etat))
    			transitions = Stream.concat(transitions.stream(), E.get(etat).stream()).collect(Collectors.toList());
    	}
    	
    	I.clear();
    	Etat i = new Etat("i");
    	I.add(i);
    	T.add(i);
    	E.put(i, transitions);
    }
    
    public void eliminerMotVide() {
    	// retains only elements which are present in a set, passed as a parameter
    	T.retainAll(T.stream().filter(etat -> !I.contains(etat)).collect(Collectors.toSet()));
    }

    public void trier() {
        for (Map.Entry<Etat, List<Transition>> entry : E.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }
}
