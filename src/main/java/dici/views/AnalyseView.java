package dici.views;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.embed.swing.SwingNode;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import dici.stats.TestDatasets;
import dici.stats.sharing.*;

public class AnalyseView 
{
	@FXML private Label lblCorrelation;
	@FXML private Label lblStrenghtCorrelation;
	@FXML private Label lblPercentCorrelation;

	@FXML private GridPane gridPane;
	@FXML private FlowPane loadPage;

	public double[][] datas; //récupèré via api ou JSP

	@FXML
	public void initialize() 
	{
		Task<Void> dataLoadingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception 
			{
				AnalyseView.this.datas = TestDatasets.DATA_GLICEMIE;
				return null;
			}
		};

		dataLoadingTask.setOnSucceeded(event -> {
			AnalysisResponse response = this.getStats("graphique");
			
			lblCorrelation        .setText( response.isCorrelated          () ? "OUI" : "NON");
			lblPercentCorrelation .setText((response.getCorrelationValue   () * 100) + " %"  );
			lblStrenghtCorrelation.setText( response.getCorrelationStrength());

			SwingNode swingNode = new SwingNode();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JComponent panel = response.getPanelGraph();
					panel.setPreferredSize(new Dimension(600,400));
					panel.revalidate(); 
					panel.repaint   (); 

					swingNode.setContent(panel);
				}
            });
		
			this.loadPage.setVisible(false);
			this.gridPane.setVisible(true );
			this.gridPane.add(swingNode,0,1);
		});

		Thread thread = new Thread(dataLoadingTask);
		thread.start();
	}


	public AnalysisResponse getStats( String nomGraphique )
	{
		String[] colNames = TestDatasets.COL_NAMES_GLICEMIE;

		// if( nomGraphique == "graphique prix / revenu")
		// {
		// 	colNames = {"prix", "revenu moyen"};
		// }

		AnalysisRequest request = new AnalysisRequest( this.datas, colNames );
		
		return request.sendRequest();
	}
}

