import java.util.Objects;

public class Transition implements Comparable<Transition> {
    private Etat courant;
    private Etat cible;
    private String symbole;

    public Etat getCourant() {
        return courant;
    }

    public void setCourant(Etat courant) {
        this.courant = courant;
    }

    public Etat getCible() {
        return cible;
    }

    public void setCible(Etat cible) {
        this.cible = cible;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public Transition(Etat courant, String symbole, Etat cible) {
        this.courant = courant;
        this.symbole = symbole;
        this.cible = cible;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Transition))
            return false;

        Transition transition = (Transition) obj;
        return this.courant.equals(transition.courant) && this.cible.equals(transition.cible) && this.symbole.equals(transition.symbole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courant, cible, symbole);
    }

    @Override
    public String toString() {
        return courant + " " + symbole + " " + cible;
    }

    @Override
    public int compareTo(Transition transition) {
        int symboleEquality = this.symbole.compareTo(transition.symbole);

        if (symboleEquality == 0) {
            return this.cible.getNom().compareTo(transition.cible.getNom());
        }

        return symboleEquality;
    }
}
