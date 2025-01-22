package dici.sharing;

import dici.stats.methods.Pearson;
import dici.stats.objects.XYCollection;
import dici.stats.objects.XYCollectionTools;
import dici.stats.methods.IMethod;

public class AnalysisRequest {
	private double[][] data;
	private String[] colNames;

	public AnalysisRequest( double[][] data, String[] colNames ){
		this.data = data;
		this.colNames = colNames;
	}

	public AnalysisResponse sendRequest(){
		/*IMethod method = new Pearson( XYCollectionTools.getXYCollection(data, colNames) );
		return new AnalysisResponse( method.compute() );*/
	}
}
