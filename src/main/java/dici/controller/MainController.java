package dici.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

import dici.classes.City;


public class MainController {

	@FXML
	private Pane contenuDynamique; // Pane dynamique pour charger les vues

	@FXML
	private Button btnAdd; // Bouton pour ajouter une ville

	@FXML
	private TextField txtCity; // Champ de texte pour saisir une ville

	@FXML
	private TableView<City> tableCity; // TableView pour afficher les villes

	private ObservableList<City> listCity; // Liste des villes

	@FXML
	private TableColumn<City, String> cityNameColumn;
	@FXML
	private TableColumn<City, Button> cityActionColumn;

	@FXML
	public void initialize() {
		listCity = FXCollections.observableArrayList();

	
	}


	// Afficher la vue pour gérer les villes
	public void afficherVueVille() {
		chargerVue("/dici/fxml/fenetre_ville.fxml");
	}

	// Afficher la vue pour gérer le nombre d'habitants
	public void afficherVueNbHabitant() {
		chargerVue("/dici/fxml/fenetre_habitant.fxml");
	}

	// Charger une vue FXML dans le panneau dynamique
	public void chargerVue(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Pane nouvelleVue = loader.load();

			// Adapter la taille de la nouvelle vue à celle du panneau
			nouvelleVue.prefWidthProperty ().bind(contenuDynamique.widthProperty());
			nouvelleVue.prefHeightProperty().bind(contenuDynamique.heightProperty());

			// Nettoyer et ajouter la nouvelle vue
			contenuDynamique.getChildren().clear();
			contenuDynamique.getChildren().add(nouvelleVue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVille() {
		String city = txtCity.getText();
	
		if (city == null || city.trim().isEmpty()) {
			System.out.println("Veuillez entrer un nom de ville valide.");
			return;
		}
	
		City newItem = new City(city.trim());
		newItem.getDeleteBtn().setOnAction(e -> removeCity(newItem));
		listCity.add(newItem);
		
		this.updateTable();
		txtCity.clear();
	}
	
	public void removeCity(City city) { listCity.remove(city); }


	public void updateTable() 
	{
		cityNameColumn  .setCellValueFactory(cellData -> new SimpleStringProperty  (cellData.getValue().getNameCity ()));
		cityActionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDeleteBtn()));

		tableCity.setItems(listCity);
	}

	// Synchroniser les valeurs d'un curseur et d'un champ de texte (fonctionalité à implémenter si nécessaire)
	public void syncValueSliderTextField() {
		// Fonctionnalité non implémentée
	}
}


