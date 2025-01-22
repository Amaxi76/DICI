package dici.stats;

import dici.stats.graph.LineGraph;
import dici.stats.methods.IMethod;
import dici.stats.methods.Pearson;
import dici.stats.methods.PearsonTools;
import dici.stats.objects.XYCollection;
import dici.stats.objects.XYCollectionTools;

public class TestData {
	public static final double[][] DATA_GLICEMIE = {
		{1.08, 68},
		{0.84, 55.4},
		{0.72, 69},
		{0.62, 61.6},
		{0.75, 65.7},
		{0.77, 68.4},
		{0.72, 75},
		{0.57, 53},
		{1.02, 85.5},
		{1.09, 93.6},
		{0.99, 72.3},
		{0.78, 68},
		{0.81, 57},
		{0.91, 58},
		{1.06, 53},
		{1.05, 94},
		{0.94, 71.5},
		{1.15, 72},
		{1.03, 56},
		{1.07, 73}
	};

	//https://www.nagwa.com/fr/explainers/143190760373/
	public static final double[][] DATA_SAUT = {
		{5.51, 1.65},
		{5.72, 1.77},
		{5.81, 1.83},
		{5.88, 1.77},
		{5.91, 1.77},
		{6.05, 1.77},
		{6.08, 1.8},
		{6.1, 1.77},
		{6.16, 1.8},
		{6.19, 1.86},
		{6.31, 1.86},
		{6.31, 1.83},
		{6.34, 1.89},
		{6.48, 1.86},
		{6.58, 1.98}
	};

	// https://datatab.fr/tutorial/correlation
	public static final String[] COL_NAMES_TAILLE_POIDS = {"taille", "poids"};
	public static final double[][] DATA_TAILLE_POIDS = {
		{1.62, 53},
		{1.72, 71},
		{1.85, 85},
		{1.82, 86},
		{1.72, 76},
		{1.55, 62},
		{1.65, 68},
		{1.77, 77},
		{1.83, 97},
		{1.53, 65}
	};

	public static void main(String[] args) {
		XYCollection datas = XYCollectionTools.getXYCollection( DATA_TAILLE_POIDS, COL_NAMES_TAILLE_POIDS ); //DATA_GLICEMIE	DATA_SAUT	DATA_TAILLE_POIDS
		IMethod pearson = new Pearson( datas );

		double correlation = pearson.compute();
		System.out.println( "value of correlation: " + correlation );
		System.out.println( "correlation is " + PearsonTools.getStringCorrelation(correlation) );
		System.out.println( "correlation is " + PearsonTools.getStringGrowth(correlation) );
		System.out.println( "correlation is " + PearsonTools.isCorrelated(correlation) );

		LineGraph graph = new LineGraph( datas );
		graph.show();
	}
}
