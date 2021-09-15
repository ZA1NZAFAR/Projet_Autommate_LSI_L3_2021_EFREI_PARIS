import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Getter
@Setter
public class Automate {
   private final LinkedHashSet<String> A = Sets.newLinkedHashSet();
    private  final LinkedHashSet<String> Q = Sets.newLinkedHashSet();
    private final LinkedHashSet<String> I = Sets.newLinkedHashSet();
    private final LinkedHashSet<String> T = Sets.newLinkedHashSet();
    private final LinkedHashMap<String, ArrayList<Transition>> E = Maps.newLinkedHashMap();

    public int readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("automate.txt"));
        String line = br.readLine();
        int alphabetCount = Integer.parseInt(line);
        char character = 'a';

        // Alphabet - A
        for (int i = 0; i < alphabetCount; i++) {
            A.add(String.valueOf(character++));
        }

        // Sommets - Q
        line = br.readLine();
        int sommetCount = Integer.parseInt(line);

        int sommet = 1;
        for (int i = 0; i < sommetCount; i++) {
            Q.add(String.valueOf(sommet++));
        }

        // Etats initiales - I
        line = br.readLine();
        String[] values = line.split("\\s");

        for (int i = 1; i <= Integer.parseInt(values[0]); i++) {
            I.add(values[i]);
        }

        // Etats terminales - T
        line = br.readLine();
        values = line.split("\\s");

        for (int i = 1; i <= Integer.parseInt(values[0]); i++) {
            T.add(values[i]);
        }

        // Transition - E
        line = br.readLine();
        do {
            values = line.split("\\s");
            int depart = Integer.parseInt(values[0]);
            char value = values[1].charAt(0);
            int arrivee = Integer.parseInt(values[2]);

            Transition transition = new Transition(depart, arrivee, value);

            ArrayList<Transition> list = E.get(values[0]) == null ? new ArrayList<>() : E.get(values[0]);
            list.add(transition);
            E.put(values[0], list);

            line = br.readLine();
        } while (line != null);

        System.out.println(A);
        System.out.println(Q);
        System.out.println(I);
        System.out.println(T);
        System.out.println(E);

        br.close();
        return 1;
    }
}
