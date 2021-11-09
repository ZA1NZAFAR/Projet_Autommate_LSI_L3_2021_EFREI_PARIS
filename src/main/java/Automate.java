import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Automate {
    private final Set<String> A = Sets.newLinkedHashSet();
    private final Set<Etat> Q = Sets.newLinkedHashSet();
    private final Set<Etat> I = Sets.newLinkedHashSet();
    private final Set<Etat> T = Sets.newLinkedHashSet();
    private final Map<Etat, List<Transition>> E = Maps.newLinkedHashMap();
    
    public Set<Etat> getI() {
    	return I;
    }
    
    public Set<Etat> getT() {
    	return T;
    }
    
    public List<Transition> getTransitions(Etat etat) {
    	return E.get(etat);
    }

    public void readFromFile(String fileName) throws IOException {
        @SuppressWarnings("resource")
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
    	String indent = String.format("%-" + (A.size() * (Q.size() * 2)) + "s", "");			// spaces between columns

        System.out.print("Etats" + indent.substring(0, indent.length() - "Etats".length()));
        for (String a : A) {
            System.out.print(a + indent.substring(0, indent.length() - 1));
        }
        
        System.out.println("");
        for (Map.Entry<Etat, List<Transition>> e : E.entrySet()) {
            System.out.print(e.getKey() + indent.substring(0, indent.length() - e.getKey().getNom().length()));
            
            for (String a : A) {
                System.out.print(
                		// Collect to list the transitions that have 'a' as a symbol
                        e.getValue().stream().filter(t -> t.getSymbole().equals(a)).collect(Collectors.toList())
                                .stream()
                                // get their end vertices (etats cibles)
                                .map(transition -> transition.getCible())
                                // Store them in a list, add indentation for each vertex (etat)
                                .collect(Collectors.toList()) + indent.substring(0, indent.length() - 
                                e.getValue().stream().filter(t -> t.getSymbole().equals(a)).collect(Collectors.toList())
                                .stream().map(transition -> transition.getCible()).collect(Collectors.toList()).toString().length()));
            }
            
            System.out.println();
        }
        
        System.out.println("\nLes etats initiaux : " + I);
        System.out.println("Les etats terminaux : " + T);
        
        String isDeterministe = isDeterministe() == true ? "est deterministe" : "n'est pas deterministe";
        System.out.println("L'automate " + isDeterministe);
        String isComplet = isComplet() == true ? "est complet" : "n'est pas complet";
        System.out.println("L'automate " + isComplet);
        String isStandard = isStandard() == true ? "est standard" : "n'est pas standard";
        System.out.println("L'automate " + isStandard);
    }
    
    public boolean isDeterministe() {
    	if (I.size() != 1)
    		return false;
    	
    	for (List<Transition> transitions : E.values()) {
    		// Maps numbers of occurrences of each alphabet symbol to symbol itself, then filters out symbols with occurrences > 1
    		if (transitions.stream().collect(Collectors.groupingBy(transition -> transition.getSymbole(), Collectors.counting()))
    				.entrySet().stream().filter(occurrence -> occurrence.getValue() > 1).collect(Collectors.toList()).size() != 0)
    			return false;
    	}
    	
    	return true;
    }
    
    public void determiniser() {
    	Map<Etat, List<Transition>> automateDeterminise = Maps.newLinkedHashMap();
    	List<Transition> transitions = new ArrayList<>();
    	StringBuilder sb = new StringBuilder();
    	
    	I.forEach(etat -> sb.append(etat.getNom()));
    	Etat etatCourant = new Etat(sb.toString());
    	
    	I.clear();
    	I.add(etatCourant);
    	automateDeterminise.put(etatCourant, new ArrayList<>());
    	
    	for (int i = 0; i < sb.length(); i++) {
    		final int index = i;
    		List<Etat> etats = E.keySet().stream().filter(etat -> etat.getNom().equals(String.valueOf(sb.charAt(index))))
    				.collect(Collectors.toList());
    		
    		for (Etat etat : etats)
    			transitions = Lists.newArrayList(Iterables.concat(transitions, E.get(etat)));
    	}
    	
    	sb.setLength(0);
    	
    	for (String symbole : A) {
    		transitions.stream().filter(transition -> transition.getSymbole().equals(symbole)).collect(Collectors.toSet())
    		.stream().map(t -> t.getCible()).collect(Collectors.toSet()).forEach(t -> sb.append(t));
    		Etat etatCible = new Etat(sb.toString());
    		Transition newTransition = new Transition(etatCourant, symbole, etatCible);
    		automateDeterminise.get(etatCourant).add(newTransition);
    		sb.setLength(0);
    	}

    	determinisationRecursive(automateDeterminise, automateDeterminise.get(etatCourant));
    	
    	Set<Etat> newT = Sets.newLinkedHashSet();
    	T.forEach(etatT -> {
    		Set<Etat> etats = automateDeterminise.keySet();
    		
    		etats.forEach(etat -> {
    			String nomEtat = etat.getNom();
    			
    			for (int i = 0; i < nomEtat.length(); i++) {
    				if (nomEtat.charAt(i) == etatT.getNom().charAt(0))
    					newT.add(etat);
    			}
    		});
    	});
    	
    	T.clear();
    	T.addAll(newT);
    	E.clear();
    	// Filter out empty entries
    	E.putAll(automateDeterminise.entrySet().stream().filter(entry -> entry.getKey().getNom().length() > 0)
    			.collect(Collectors.toMap(automate -> automate.getKey(), automate -> automate.getValue())));
    }
    
    private void determinisationRecursive(Map<Etat, List<Transition>> automateDeterminise, List<Transition> transitions) {
    	List<Transition> newTransitions = new ArrayList<>();
    	StringBuilder sb = new StringBuilder();
    	
    	for (Transition transition : transitions) {
    		Etat etatCourant = transition.getCible();
    		if (!automateDeterminise.containsKey(etatCourant)) {
    			automateDeterminise.put(etatCourant, new ArrayList<>());
    		}
    		
    		for (int i = 0; i < etatCourant.getNom().length(); i++) {
        		final String nomEtat = String.valueOf(etatCourant.getNom().charAt(i));
        		List<Etat> etats = E.keySet().stream().filter(etat -> etat.getNom().equals(nomEtat)).collect(Collectors.toList());
        		
        		for (Etat etat : etats)
        			newTransitions = Lists.newArrayList(Iterables.concat(newTransitions, E.get(etat)));
        	}
        	
        	for (String symbole : A) {
        		// Store to set all transitions with symbol equal to 'symbole'
        		newTransitions.stream().filter(t -> t.getSymbole().equals(symbole))
        		// Convert to stream, consisting of only end vertices (etats cibles). Using set prevents storing duplicate values
        				.collect(Collectors.toSet()).stream().map(t -> t.getCible()).collect(Collectors.toSet())
        		.forEach(t -> sb.append(t));
        		
        		if (sb.length() > 0) {
        			final Etat etatCible = new Etat(sb.toString());
        			sb.setLength(0);	// this little shit belongs here
        			
        			// Prevent stack overflow if the end vertex is the same and the origin (e.g.: 5 -> 5)
        			if (automateDeterminise.containsKey(etatCible) && etatCourant.equals(etatCible)) {
        				// But store the transition if it uses a different symbol (e.g.: 5 a 5 != 5 b 5)
        				if (automateDeterminise.get(etatCible).stream().filter(t -> t.getCible().equals(etatCible))
        						.collect(Collectors.toList()).size() != 0 && automateDeterminise.get(etatCible).stream()
        						.filter(t -> t.getSymbole().equals(symbole)).collect(Collectors.toList()).size() != 0)
        						break;
        			}
        			
            		Transition newTransition = new Transition(etatCourant, symbole, etatCible);
            		if (!automateDeterminise.get(etatCourant).contains(newTransition))
            			automateDeterminise.get(etatCourant).add(newTransition);
        		}
        	}
        	
        	newTransitions.clear();
        	// Do not visit the vertices, which have been already visited
        	determinisationRecursive(automateDeterminise, automateDeterminise.get(etatCourant).stream().
        			filter(t -> !automateDeterminise.containsKey(t.getCible())).collect(Collectors.toList()));
    	}
    }
    
    public boolean isComplet() {
        for (Map.Entry<Etat, List<Transition>> transition : E.entrySet()) {
        	for (String symbole : A) {
        		if (transition.getValue().stream().filter(t -> t.getSymbole().equals(symbole))
        				.collect(Collectors.toList()).size() == 0)
        			return false;
        	}
        }
       
        for (Etat etat : T) {
        	if(!E.containsKey(etat))
        			return false;
       
        	for (String symbole : A) {
        		if (E.get(etat).stream().filter(transition -> transition.getSymbole().equals(symbole))
        				.collect(Collectors.toList()).size() == 0)
        			return false;
        	}
        }
       
        return true;
    }
       
    public void completer() {
    	Etat p = new Etat("P");
    	
    	for (Map.Entry<Etat, List<Transition>> transition : E.entrySet()) {
        	for (String symbole : A) {
        		if (transition.getValue().stream().filter(t -> t.getSymbole().equals(symbole))
        				.collect(Collectors.toList()).size() == 0) {
        			transition.getValue().add(new Transition(transition.getKey(), symbole, p));
        		}
        	}
        }
    	
    	for (Etat etat : T) {
        	if(!E.containsKey(etat)) {
        		E.put(etat, new ArrayList<>());
        		for (String symbole : A) {
            		if (E.get(etat).stream().filter(transition -> transition.getSymbole().equals(symbole))
            				.collect(Collectors.toList()).size() == 0) {
            			E.get(etat).add(new Transition(etat, symbole, p));
            		}
            	}
        	}
        }
    	
    	E.put(p, new ArrayList<>());
    	for (String symbole : A)
    		E.get(p).add(new Transition(p, symbole, p));
    }
    
    public boolean reconnaitLeMot(List<Transition> currentTransitions, String mot) {
    	int charIndex = 0;
    	final String character = String.valueOf(mot.charAt(charIndex));
    	
    	// find last vertex cycle and store it's value
    	int lastCycle = 0;
    	List<Etat> etats = ImmutableList.copyOf(Q);
    	for (int i = Q.size() - 1; i >= 0; i--) {
    		Etat etat = etats.get(i);
    		if (E.containsKey(etat)) {
    			List<Transition> cycle = E.get(etat).stream().filter(t -> t.getCible().equals(etat)).collect(Collectors.toList());
    			if (cycle.size() > 0) {
    				lastCycle = i + 1;
    				break;
    			}
    		}
    	}
    	
    	if (mot.length() == Q.size() - lastCycle) {
    		currentTransitions.removeIf(t -> t.getCourant().equals(t.getCible()));
    	}	
    	
    	// if the last vertex contains a cycle
    	if (currentTransitions.isEmpty()) {
    		for (Etat etat : T) {
    			if (E.containsKey(etat))
    					return E.get(etat).stream().map(t -> t.getSymbole()).collect(Collectors.toList()).contains(character);
        	}
    	}
    	
        for (Transition transition : currentTransitions) {
        	
        	if (mot.length() <= 1 && transition.getSymbole().equals(character) && T.contains(transition.getCible())) {
        		return true;
        	} else if (mot.length() <= 1 && !transition.getSymbole().equals(character)) {
        		break;
        	}
       
        	if (mot.length() == 1)
        		break;
        	
        	String nextCharacter = String.valueOf(mot.charAt(1));
        	if (E.get(transition.getCible()) == null) {
        		break;
        	}
        	
        	List<Transition> transitions = E.get(transition.getCible()).stream().filter(t -> 
        	t.getSymbole().equals(nextCharacter)).collect(Collectors.toList());
        	
            return reconnaitLeMot(transitions, mot.substring(charIndex + 1));
        }
    	
        return false;
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
    	// Retains only elements which are present in a set, passed as a parameter
    	T.retainAll(T.stream().filter(etat -> !I.contains(etat)).collect(Collectors.toSet()));
    }
    
    public void langageComplementaire() {
    	System.out.println(T);
    	System.out.println(Q);
    	Set<Etat> notPresentInitiaux = I.stream().filter(e -> !E.keySet().contains(e)).collect(Collectors.toSet());
    	Set<Etat> notPresentTerminaux = T.stream().filter(e -> !E.keySet().contains(e)).collect(Collectors.toSet());
    	Set<Etat> notTerminal = E.keySet().stream().filter(etat -> !T.contains(etat)).collect(Collectors.toSet());
    	
    	// Add vertices which are either isolated or accept empty word with no outgoing transitions and aren't present in E. 
    	if (!notPresentInitiaux.isEmpty())
    		notPresentInitiaux.forEach(e -> notTerminal.add(e));
    	if (!notPresentTerminaux.isEmpty())
    		notPresentTerminaux.forEach(e -> notTerminal.add(e));
    	
    	T.clear();
    	notTerminal.forEach(etat -> T.add(etat));
    }

    public void trier() {
        for (Map.Entry<Etat, List<Transition>> entry : E.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }
}
