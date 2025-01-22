package dici.sharing;

import org.jfree.chart.ChartPanel;
import dici.stats.methods.IMethod;
import dici.stats.methods.PearsonTools; //TODO:  "problème" avec le fait qu'il n'y a pas d'abstraction sur les outils "Pearson"

public class AnalysisResponse {
	//private IMethod statMethod;

	private ChartPanel panelGraph;
	private boolean isCorrelated;
	private String correlationStrength;
	private double correlationValue;

	public AnalysisResponse( IMethod statMethod ){
		this.correlationValue = statMethod.compute();
  this.isCorrelated = PearsonTools.isCorrelated( this.correlationValue );
  this.correlationStrength = PearsonTools.getStringCorrelation( this.correlationValue );

  LineGraph lineGraph = new LineGraph( statsMethod.getXYCollection() ); 
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
