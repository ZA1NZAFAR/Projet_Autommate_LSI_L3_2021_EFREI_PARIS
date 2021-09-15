import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
		readFile();
    }

	static void readFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("automate.txt"));
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
	}
}
