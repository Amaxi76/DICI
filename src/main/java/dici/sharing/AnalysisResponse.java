package dici.sharing;

import javax.swing.JPanel;

import dici.stats.methods.IMethod;

public class AnalysisResponse {
	private IMethod statMethod;

	/*private JPanel panelGraph;
	private boolean isCorrelated;
	private String correlationStrength;
	private double correlationValue;*/

	public AnalysisResponse( IMethod statMethod ){
		this.statMethod = statMethod;
	}

	public JPanel getPanelGraph( ){
	}

	public boolean isCorrelated( ){
	}

	public String getCorrelationStrength( ){
	}

	public double getCorrelationValue( ){
	}
}
