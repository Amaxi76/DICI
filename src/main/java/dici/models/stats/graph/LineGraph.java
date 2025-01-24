package dici.models.stats.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import dici.models.stats.objects.XYCollection;

import javax.swing.*;

public class LineGraph{

	private XYCollection datas;
	private XYSeriesCollection dataset;

	public LineGraph( XYCollection datas ){
		this.datas = datas;
		this.dataset = new XYSeriesCollection();
		this.addData();
		this.addRegressionLine();
	}

	private void addData(){
		XYSeries series = new XYSeries("Points");

		for( int cntPoint = 0; cntPoint < datas.size(); cntPoint++ ){
			double x = datas.getValuesX().getValue(cntPoint);
			double y = datas.getValuesY().getValue(cntPoint);
			series.add(x, y);
		}

		this.dataset.addSeries(series);
	}

	private void addRegressionLine(){ // Alex I
		XYSeries regressionLine = new XYSeries("Régression");

		// Initialisation des variables pour les calculs
		double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
		int n = datas.size();
	
		// Calculs des sommes nécessaires pour la régression
		for (int cntPoint = 0; cntPoint < n; cntPoint++) {
			double x = datas.getValuesX().getValue(cntPoint);
			double y = datas.getValuesY().getValue(cntPoint);
			
			sumX += x;
			sumY += y;
			sumXY += x * y;
			sumX2 += x * x;
		}
	
		// Calcul des coefficients de la droite de régression
		double meanX = sumX / n;
		double meanY = sumY / n;
		double a = (sumXY - n * meanX * meanY) / (sumX2 - n * meanX * meanX); // Pente
		double b = meanY - a * meanX; // Ordonnée à l'origine
	
		// Affichage de l'équation pour vérification
		System.out.println("Equation de la droite : y = " + a + " * x + " + b);
	
		// Déterminer les bornes pour tracer la droite
		double globalMinX = Double.MAX_VALUE;
		double globalMaxX = -Double.MAX_VALUE;
	
		for (int cntPoint = 0; cntPoint < n; cntPoint++) {
			double x = datas.getValuesX().getValue(cntPoint);
			if (x < globalMinX) globalMinX = x;
			if (x > globalMaxX) globalMaxX = x;
		}
	
		// Ajouter une marge pour que la droite dépasse légèrement les points
		double margin = (globalMaxX - globalMinX) * 0.1;
		double extendedMinX = globalMinX - margin;
		double extendedMaxX = globalMaxX + margin;
	
		// Ajouter les points de la droite de régression
		regressionLine.add(extendedMinX, a * extendedMinX + b); // Point à gauche
		regressionLine.add(extendedMaxX, a * extendedMaxX + b); // Point à droite
	
		// Ajouter la série au dataset
		this.dataset.addSeries(regressionLine);
	}

	public JFreeChart getChart(){
		String lblX = this.datas.getValuesX().getName();
		String lblY = this.datas.getValuesY().getName();
		String title = String.format("Graphique de %s en fonction de %s et sa droite de régression.", lblX, lblY);

		JFreeChart chart = ChartFactory.createXYLineChart(
			title,
			lblX,
			lblY,
			this.dataset,
			PlotOrientation.VERTICAL,
			true,
			true,
			false
		);

		return chart;
	}
}
