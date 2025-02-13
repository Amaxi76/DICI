package dici.stats;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import dici.stats.graph.LineGraph;
import dici.stats.methods.IMethod;
import dici.stats.methods.Pearson;
import dici.stats.methods.MethodTools;
import dici.stats.objects.XYCollection;
import dici.stats.objects.XYCollectionTools;

public class TestStats {
	public static void main(String[] args) {
		XYCollection datas = XYCollectionTools.getXYCollection( TestDatasets.DATA_TAILLE_POIDS, TestDatasets.COL_NAMES_TAILLE_POIDS );
		
		IMethod pearson = new Pearson( datas );
		double correlation = pearson.compute();
		
		System.out.println( "value of correlation: " + correlation );
		System.out.println( "correlation is " + MethodTools.getStringCorrelation(correlation) );
		System.out.println( "correlation is " + MethodTools.getStringGrowth(correlation) );
		System.out.println( "correlation is " + MethodTools.isCorrelated(correlation) );

		LineGraph graph = new LineGraph( datas );
		TestStats.showChart( new ChartPanel(graph.getChart()) );
	}

	private static void showChart( ChartPanel panel ){
		JFrame frame = new JFrame("Graphique avec JFreeChart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
