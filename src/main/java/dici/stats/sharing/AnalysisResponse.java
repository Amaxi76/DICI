package dici.stats.sharing;

import org.jfree.chart.ChartPanel;

import dici.stats.graph.LineGraph;
import dici.stats.methods.IMethod;
import dici.stats.methods.MethodTools;

public class AnalysisResponse {

	private ChartPanel panelGraph;
	private boolean isCorrelated;
	private String correlationStrength;
	private double correlationValue;

	public AnalysisResponse( IMethod statMethod ){
		this.correlationValue = statMethod.compute();
		this.isCorrelated = MethodTools.isCorrelated( this.correlationValue );
		this.correlationStrength = MethodTools.getStringCorrelation( this.correlationValue );

		LineGraph lineGraph = new LineGraph( statMethod.getXYCollection() ); 
		this.panelGraph = new ChartPanel( lineGraph.getChart() );
	}

	public ChartPanel getPanelGraph( ){
		return this.panelGraph;
	}

	public boolean isCorrelated( ){
		return this.isCorrelated;
	}

	public String getCorrelationStrength( ){
		return this.correlationStrength;
	}

	public double getCorrelationValue( ){
		return this.correlationValue;
	}
}
