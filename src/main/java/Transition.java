import com.google.common.base.MoreObjects.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transition {
    int depart;
    int arrivee;
    char value;

    @Override
    public String toString() {
        return depart + " " + value + " " + arrivee;
    }
}
