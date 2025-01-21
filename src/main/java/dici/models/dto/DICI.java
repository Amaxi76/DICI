package dici.models.dto;


public class DICI {
	private int id;
	private int codeVille;
    private String nomVille;
    private int prixM2;
    private String typeZone;
	private int age;
	private String niveauDiplome;
	private int densitePop;
	private int popActive;
	private int revenuMedian;
	private int tauxChomage;

	// Constructeurs
	public DICI() {}

	public DICI(int codeVille, String nomVille, int prixM2, String typeZone, int age, String niveauDiplome, int densitePop, int popActive, int revenuMedian, int tauxChomage) {
		this.codeVille = codeVille;
		this.nomVille = nomVille;
		this.prixM2 = prixM2;
		this.typeZone = typeZone;
		this.age = age;
		this.niveauDiplome = niveauDiplome;
		this.densitePop = densitePop;
		this.popActive = popActive;
		this.revenuMedian = revenuMedian;
		this.tauxChomage = tauxChomage;
	}

	public DICI(int id, int codeVille, String nomVille, int prixM2, String typeZone, int age, String niveauDiplome, int densitePop, int popActive, int revenuMedian, int tauxChomage) {
		this.id = id;
		this.codeVille = codeVille;
		this.nomVille = nomVille;
		this.prixM2 = prixM2;
		this.typeZone = typeZone;
		this.age = age;
		this.niveauDiplome = niveauDiplome;
		this.densitePop = densitePop;
		this.popActive = popActive;
		this.revenuMedian = revenuMedian;
		this.tauxChomage = tauxChomage;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public int getCodeVille() {
		return codeVille;
	}

	public void setCodeVille(int codeVille) {
		this.codeVille = codeVille;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNiveauDiplome() {
		return niveauDiplome;
	}

	public void setNiveauDiplome(String niveauDiplome) {
		this.niveauDiplome = niveauDiplome;
	}

	public int getDensitePop() {
		return densitePop;
	}

	public void setDensitePop(int densitePop) {
		this.densitePop = densitePop;
	}

	public int getPopActive() {
		return popActive;
	}

	public void setPopActive(int popActive) {
		this.popActive = popActive;
	}

	public int getRevenuMedian() {
		return revenuMedian;
	}

	public void setRevenuMedian(int revenuMedian) {
		this.revenuMedian = revenuMedian;
	}

	public int getTauxChomage() {
		return tauxChomage;
	}

	public void setTauxChomage(int tauxChomage) {
		this.tauxChomage = tauxChomage;
	}

	// toString
	@Override
	public String toString() {
		return "DICI{id=" + id + ", codeVille=" + codeVille + ", nomVille='" + nomVille + "', prixM2=" + prixM2 + ", typeZone='" + typeZone + "', age=" + age + ", niveauDiplome='" + niveauDiplome + "', densitePop=" + densitePop + ", popActive=" + popActive + ", revenuMedian=" + revenuMedian + ", tauxChomage=" + tauxChomage + "}";
	}
}
