package dici.views;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.embed.swing.SwingNode;

import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import dici.controller.MainController;
import dici.services.DatabaseService;
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

		System.out.println();

		DatabaseService database = new DatabaseService();
		// List<String> cities = new ArrayList<String>();
		// cities.add("Toulouse");
		// cities.add("Nice");
		// cities.add("Nantes");

		List<Map<String, Object>> citiesInfo = database.getCityInfoByNames(MainController.get().getNameCity());
		Task<Void> dataLoadingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception 
			{
				AnalyseView.this.datas = new double[citiesInfo.size()][2];
				for (int i = 0; i < citiesInfo.size(); i++) 
				{
					AnalyseView.this.datas[i][0] = (double) citiesInfo.get(i).get("prix_m2");
					System.out.println(AnalyseView.this.datas[i][0]);
					AnalyseView.this.datas[i][1] = (double) citiesInfo.get(i).get("age");
					System.out.println(AnalyseView.this.datas[i][1]);
				}
				
				for (int i = 0; i < AnalyseView.this.datas.length; i++) 
				{
					for (int j = 0; j < AnalyseView.this.datas[i].length; j++) 
					{
						System.out.print(AnalyseView.this.datas[i][j] + " ");
					}
					System.out.println();
				}
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

