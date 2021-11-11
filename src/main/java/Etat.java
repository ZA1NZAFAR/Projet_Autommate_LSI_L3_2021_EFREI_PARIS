import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Etat {
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Etat(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Etat))
            return false;

        Etat etat = (Etat) obj;
        return this.nom.equals(etat.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return nom;
    }
}
