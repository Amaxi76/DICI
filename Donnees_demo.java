public class Donnees_demo {
	private int id;
	private int age;
	private String niveauDiplome;
	private int densitePop;
	private int popActive;
	private int revenuMedian;
	private int tauxChomage;

	// Constructeurs
	public Donnees_demo() {}

	public Donnees_demo(int age, String niveauDiplome, int densitePop, int popActive, int revenuMedian, int tauxChomage) {
		this.age = age;
		this.niveauDiplome = niveauDiplome;
		this.densitePop = densitePop;
		this.popActive = popActive;
		this.revenuMedian = revenuMedian;
		this.tauxChomage = tauxChomage;
	}

	public Donnees_demo(int id, int age, String niveauDiplome, int densitePop, int popActive, int revenuMedian, int tauxChomage) {
		this.id = id;
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

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Donnees_demo{id=" + id + ", age=" + age + ", niveauDiplome='" + niveauDiplome + "', densitePop=" + densitePop + ", popActive=" + popActive + ", revenuMedian=" + revenuMedian + ", tauxChomage=" + tauxChomage + "}";
	}
}
