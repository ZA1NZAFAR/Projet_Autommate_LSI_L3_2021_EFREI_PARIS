import com.google.common.base.MoreObjects.ToStringHelper;

public class Transition {
	int depart;
	int arrivee;
	char value;
	
	public Transition(int depart, int arrivee, char value) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.value = value;
	}

	public int getDepart() {
		return depart;
	}

	public void setDepart(int depart) {
		this.depart = depart;
	}

	public int getArrivee() {
		return arrivee;
	}

	public void setArrivee(int arrivee) {
		this.arrivee = arrivee;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return depart + " " + value + " " + arrivee;
	}
}
