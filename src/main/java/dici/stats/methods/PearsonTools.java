package dici.stats.methods;

public class PearsonTools {
	
	public static boolean isCorrelated( double factor ){
		return factor > 0.7;
	}

	public static String getStringGrowth( double factor ){
		if( factor > 0 ) return "croissance";
		if( factor < 0 ) return "décroissance";
		return "stable"; 
	}

	public static String getStringCorrelation( double factor ){
		if( factor < 0.3 ) return "faible";
		if( factor < 0.7 ) return "modéré";
		return "fort";
	}
}
