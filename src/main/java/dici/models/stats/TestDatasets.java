package dici.models.stats;

public class TestDatasets {
	public static final String[] COL_NAMES_GLICEMIE = {"taux glicemie", "poids"};
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

	public static final String[] COL_NAMES_VILLE_AGE = {"prix m2", "age moyen"};
	public static final double[][] DATA_VILLE_AGE = {
            {3932  ,         51 },
			{3808.346923828125,   36},
			{2772.4130859375 , 39 },
			{3539.7734375, 36 },
			{2419.150634765625, 37 },
			{2742.863525390625, 39 },
			{2902.0869140625, 39 },
			{2630.916015625, 38 },
			{3127.647216796875, 38 }
	};
	
	//https://www.nagwa.com/fr/explainers/143190760373/
	public static final String[] COL_NAMES_SAUT = {"distance", "hauteur personne"};
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
}
