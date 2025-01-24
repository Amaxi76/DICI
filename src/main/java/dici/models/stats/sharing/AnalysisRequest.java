package dici.models.stats.sharing;

import dici.models.stats.methods.IMethod;
import dici.models.stats.methods.Pearson;
import dici.models.stats.objects.XYCollectionTools;

public class AnalysisRequest {
	private double[][] data;
	private String[] colNames;

	public AnalysisRequest( double[][] data, String[] colNames ){
		this.data = data;
		this.colNames = colNames;
	}

	public AnalysisResponse sendRequest(){
		IMethod method = new Pearson( XYCollectionTools.getXYCollection(data, colNames) );
		return new AnalysisResponse( method );
	}
}
