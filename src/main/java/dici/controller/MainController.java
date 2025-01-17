package dici.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController 
{
	@FXML
	private Pane contenuDynamique;
	@FXML
	private Button btnAdd;
	@FXML 
	private TextField txtCity;
	@FXML
	private FlowPane listCity;

	public void afficherVueVille     () { chargerVue("/com/example/fxml/fenetre_ville.fxml"   );}
	public void afficherVueNbHabitant() { chargerVue("/com/example/fxml/fenetre_habitant.fxml");}

	public void chargerVue(String fxml)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Pane nouvelleVue  = loader.load();

			nouvelleVue.prefWidthProperty ().bind(contenuDynamique.widthProperty ());
        	nouvelleVue.prefHeightProperty().bind(contenuDynamique.heightProperty());

			contenuDynamique.getChildren().clear();
			contenuDynamique.getChildren().add(nouvelleVue);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVille()
	{
		Button btnNewVille = new Button(txtCity.getText());
		listCity.getChildren().add(btnNewVille);
	}
}
