package dici.sharing;

import javax.swing.JPanel;

import dici.stats.methods.IMethod;

public class AnalysisResponse {
	//private IMethod statMethod;

	private JPanel panelGraph;
	private boolean isCorrelated;
	private String correlationStrength;
	private double correlationValue;

	public AnalysisResponse( IMethod statMethod ){
		this.correlationValue = statMethod.compute();
  this.isCorrelated = PearsonTools.isCorrelated( this.correlationValue );
  this.correlationStrength = PearsonTools.getStringCorrelation( this.correlationValue );

  LineGraph lineGraph = new LineGraph( statsMethod.getXYCollection() ); 
  this.panelGraph = lineGraph.getPanel();
	}

	public JPanel getPanelGraph( ){
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
