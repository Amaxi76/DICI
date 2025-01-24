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

import dici.controllers.MainController;
import dici.models.dao.DatabaseService;
import dici.models.stats.TestDatasets;
import dici.models.stats.sharing.*;

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
		DatabaseService database = new DatabaseService();
		
		// Map<String, List<String>> citiesInfo = database.getCityInfoByNames(MainController.get().getNameCity());
		// Task<Void> dataLoadingTask = new Task<Void>() {
		// 	@Override
		// 	protected Void call() throws Exception 
		// 	{
		// 		int rowCount = citiesInfo.size();
		// 		int colCount = citiesInfo.values().stream().mapToInt(List::size).max().orElse(0);
		// 		AnalyseView.this.datas = new double[rowCount][colCount];

		// 		int i = 0;
		// 		for(Map.Entry<String,List<String>> entry : citiesInfo.entrySet())
		// 		{
		// 			List<String> values = entry.getValue();
		// 			for (int j = 0; j < values.size(); j++) 
		// 			{
		// 				try {
		// 					AnalyseView.this.datas[i][j] = Double.parseDouble(values.get(j));
		// 				} catch (NumberFormatException e) {
		// 					System.err.println("Erreur de parsing pour la valeur: " + values.get(j));
		// 					AnalyseView.this.datas[i][j] = 0.0; // ou une autre valeur par défaut
		// 				}
		// 			}
		// 			i++;
		// 		}
				
		// 		// Affichage pour debug
		// 		System.out.println(AnalyseView.this.datas.length);
		// 		for (double[] row : AnalyseView.this.datas) 
		// 		{
		// 			for (double value : row) 
		// 			{
		// 				System.out.print(value + " ");
		// 			}
		// 			System.out.println();
		// 		}
		// 		return null;
		// 	}
		// };

		// dataLoadingTask.setOnSucceeded(event -> {
		// 	AnalysisResponse response = this.getStats("graphique");
			
		// 	lblCorrelation        .setText( response.isCorrelated          () ? "OUI" : "NON");
		// 	lblPercentCorrelation .setText((response.getCorrelationValue   () * 100) + " %"  );
		// 	lblStrenghtCorrelation.setText( response.getCorrelationStrength());

		// 	SwingNode swingNode = new SwingNode();

		// 	SwingUtilities.invokeLater(new Runnable() {
		// 		@Override
		// 		public void run() {
		// 			JComponent panel = response.getPanelGraph();
		// 			panel.setPreferredSize(new Dimension(600,400));
		// 			panel.revalidate(); 
		// 			panel.repaint   (); 

		// 			swingNode.setContent(panel);
		// 		}
        //     });
		
		// 	this.loadPage.setVisible(false);
		// 	this.gridPane.setVisible(true );
		// 	this.gridPane.add(swingNode,0,1);
		// });

		// Thread thread = new Thread(dataLoadingTask);
		// thread.start();
	}

	public AnalysisResponse getStats( String nomGraphique )
	{
		String[] colNames = TestDatasets.COL_NAMES_VILLE_AGE;

		// if( nomGraphique == "graphique prix / revenu")
		// {
		// 	colNames = {"prix", "revenu moyen"};
		// }

		AnalysisRequest request = new AnalysisRequest( this.datas, colNames );
		
		return request.sendRequest();
	}
}

