import com.google.common.base.MoreObjects.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transition implements Comparable {
    int depart;
    int arrivee;
    char value;

    @Override
    public String toString() {
        return depart + " " + value + " " + arrivee;
    }

    @Override
    public int compareTo(Object o) {
        Transition obj = (Transition) o;
        if (this.value > obj.value)
            return 1;
        else if (this.value < obj.value)
            return -1;
        else return 0;
    }
}
