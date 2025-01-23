package dici.stats.methods;

import dici.stats.objects.XYCollection;

public class Pearson implements IMethod {
    private XYCollection datas;

    public Pearson(XYCollection datas) {
        this.datas = datas;
    }

    @Override
    public XYCollection getXYCollection() {
        return this.datas;
    }

    @Override
    public double compute() {
        // Calcul du numérateur
        double numerator = 0;
        double avgX = this.datas.getValuesX().avg();
        double avgY = this.datas.getValuesY().avg();
        
        // On calcule la somme des produits (xi - moyenneX) * (yi - moyenneY)
        for (int i = 0; i < this.datas.size(); i++) {
            double x = this.datas.getValuesX().getValue(i);
            double y = this.datas.getValuesY().getValue(i);
            numerator += (x - avgX) * (y - avgY);
        }

        // Calcul du dénominateur (écarts-types des deux variables)
        double denominatorX = 0;
        double denominatorY = 0;

        for (int i = 0; i < this.datas.size(); i++) {
            double x = this.datas.getValuesX().getValue(i);
            double y = this.datas.getValuesY().getValue(i);
            denominatorX += Math.pow(x - avgX, 2);
            denominatorY += Math.pow(y - avgY, 2);
        }

        // Racine carrée des sommes des carrés
        double denominator = Math.sqrt(denominatorX * denominatorY);

        // Retourne le coefficient de corrélation de Pearson
        return numerator / denominator;
    }
}

// je pense qu'elle n'est pas bonne
/*package dici.stats.methods;

import dici.stats.objects.XYCollection;

public class Pearson implements IMethod {
	private XYCollection datas;

	public Pearson( XYCollection datas ){
		this.datas = datas;
	}

	@Override
	public XYCollection getXYCollection(){
		return this.datas;
	}

	@Override
	public double compute(){
		return this.numerator() / this.denominator();
	}

	private double numerator(){
		return this.datas.covariance();
	}

	private double denominator(){
		double avgX = this.datas.getValuesX().avg();
		double avgY = this.datas.getValuesX().avg();
		double sumX = 0;
		double sumY = 0;

		for(int i = 0; i < this.datas.size(); i++){
			double x = this.datas.getValuesX().getValue(i);
			double y = this.datas.getValuesX().getValue(i);

			sumX += Math.pow(x - avgX, 2);
			sumY += Math.pow(y - avgY, 2);
		}

		return Math.sqrt(sumX * sumY);
	}
}*/
