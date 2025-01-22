package dici.views;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class AnalyseView 
{
	@FXML private Label lblCorrelation;
	@FXML private Label lblStrenghtCorrelation;
	@FXML private Label lblPercentCorrelation;

	@FXML private GridPane gridPane;
	@FXML private FlowPane loadPage;

	private double[][] datas; //récupèré via api ou JSP

	
	@FXML
	public void initialize() {
		Task<Void> dataLoadingTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				datas = // Méthode pour récupérer les datas
				return null;
			}
		};

		dataLoadingTask.setOnSucceeded(event -> {
			ResponseStats rs = this.getStats("graphique");
			
			lblCorrelation.setText(rs.getCorrelation());
			lblPercentCorrelation.setText(rs.getPercentCorrelation());
			lblStrenghtCorrelation.setText(rs.getStrenghtCorrelation());
			this.gridPane.add(rs.getGraphPanel(), 0, 1);
			
			loadPage.setVisible(false);
			gridPane.setVisible(true);
		});

		Thread thread = new Thread(dataLoadingTask);
		thread.start();
	}


	public ResponseStats getStats( String nomGraphique )
	{
		if( nomGraphique == "graphique prix / revenu")
		{
			String[] colNames = {"prix", "revenu moyen"};
		}

		RequestStat rs = new RS( this.datas, colNames );
		return rs.request();
	}
}

