

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Automate {
	static final LinkedHashSet<String> A = Sets.newLinkedHashSet();
	static final LinkedHashSet<String> Q = Sets.newLinkedHashSet();
	static final LinkedHashSet<String> I = Sets.newLinkedHashSet();
	static final LinkedHashSet<String> T = Sets.newLinkedHashSet();
	static final LinkedHashMap<String, ArrayList<String>> E = Maps.newLinkedHashMap();
	
	public int readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("automate.txt"));
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		
		return -1;
	}
}
