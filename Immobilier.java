// Classe représentant un enregistrement de la table "immobilier"
public class Immobilier {
    private int id;
    private String nomVille;
    private int prixM2;
    private String typeZone;

    // Constructeurs
    public Immobilier() {}

    public Immobilier(String nomVille, int prixM2, String typeZone) {
        this.nomVille = nomVille;
        this.prixM2 = prixM2;
        this.typeZone = typeZone;
    }

    public Immobilier(int id, String nomVille, int prixM2, String typeZone) {
        this.id = id;
        this.nomVille = nomVille;
        this.prixM2 = prixM2;
        this.typeZone = typeZone;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public int getPrixM2() {
        return prixM2;
    }

    public void setPrixM2(int prixM2) {
        this.prixM2 = prixM2;
    }

    public String getTypeZone() {
        return typeZone;
    }

    public void setTypeZone(String typeZone) {
        this.typeZone = typeZone;
    }

    @Override
    public String toString() {
        return "Immobilier{id=" + id + ", nomVille='" + nomVille + "', prixM2=" + prixM2 + ", typeZone='" + typeZone + "'}";
    }
}