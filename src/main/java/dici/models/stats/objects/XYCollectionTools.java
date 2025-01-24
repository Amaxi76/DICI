package dici.models.stats.objects;

public class XYCollectionTools {
	public static XYCollection getXYCollection( double[][] data, String[] colNames ){

		double[] x = new double[data.length];
		double[] y = new double[data.length];

		for( int cnt=0; cnt < data.length; cnt++ ){
			x[cnt] = data[cnt][0];
			y[cnt] = data[cnt][1];
		}

		assert colNames.length == 2 : "The number of columns must be 2";
		String nameX = colNames[0];
		String nameY = colNames[1];

		ValuesCollection valuesX = new ValuesCollection(nameX, x);
		ValuesCollection valuesY = new ValuesCollection(nameY, y);
		return new XYCollection(valuesX, valuesY);
	}
}
