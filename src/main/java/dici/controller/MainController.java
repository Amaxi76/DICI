package dici.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import dici.classes.City;

public class MainController {

	@FXML
	private GridPane formVille;
	@FXML
	private VBox     formHabitant;
	@FXML
	private FlowPane loadPage;

	@FXML
	private Button btnAdd; // Bouton pour ajouter une ville
	@FXML
	private TextField txtCity; // Champ de texte pour saisir une ville
	@FXML
	private TextField txtArrondi; // Champ de texte pour l'arrondi
	@FXML
	private Slider sliderArrondi; // Slider pour ajuster les valeurs

	@FXML
	private RadioButton radioPlus10;
	@FXML
	private RadioButton radioPlus100;
	@FXML
	private RadioButton radioPlus1000;

	private ToggleGroup toggleGroup;

	@FXML
	private TableView<City> tableCity; // TableView pour afficher les villes
	@FXML
	private TableColumn<City, String> cityNameColumn;
	@FXML
	private TableColumn<City, Button> cityActionColumn;

	private ObservableList<City> listCity; // Liste observable des villes

	@FXML
	public void initialize() {
		// Initialisation des éléments partagés
		listCity = FXCollections.observableArrayList();

		tableCity.setItems(listCity);

		this.setToggleGroup();
		this.setupListeners();
	}

	// Méthode pour afficher une vue spécifique
	public void afficherVue(String vue) {
		boolean isVille = "ville".equals(vue);
		boolean isHabitant = "habitant".equals(vue);

		formVille.setVisible(isVille);
		formVille.setManaged(isVille);

		formHabitant.setVisible(isHabitant);
		formHabitant.setManaged(isHabitant);

		loadPage.setVisible(false);
		loadPage.setManaged(false);
	}

	// Afficher la vue pour gérer les villes
	public void afficherVueVille() {
		afficherVue("ville");
	}

	// Afficher la vue pour gérer le nombre d'habitants
	public void afficherVueNbHabitant() {
		afficherVue("habitant");
	}

	// Configurer les listeners pour le slider
	public void setupListeners() {
		if (sliderArrondi != null) {
			sliderArrondi.valueProperty().addListener((observable, oldValue, newValue) -> {
				syncValueSliderTextField();
			});
		}
	}

	// Configurer le groupe de boutons radio
	public void setToggleGroup() {
		// Créer un groupe pour les boutons radio
		toggleGroup = new ToggleGroup();
		radioPlus10  .setToggleGroup(toggleGroup);
		radioPlus100 .setToggleGroup(toggleGroup);
		radioPlus1000.setToggleGroup(toggleGroup);

		// Ajouter un écouteur au groupe
		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				RadioButton selectedRadio = (RadioButton) newValue;
				handleRadioButtonClick(selectedRadio.getText());
			}
		});
	}

	// Méthode pour gérer les clics sur les boutons radio
	private void handleRadioButtonClick(String text) 
	{
		System.out.println("je suis la");
		double increment;
		int max;
		switch (text) {
			case "+10":
				increment = 10;
				max       = 100; 
				break;
			case "+100":
				increment = 100;
				max       = 1000;
				break;
			case "+1000":
				increment = 1000;
				max       = 10000;
				break;
			default:
				increment = 10;
				max       = 100;
				break;
		}
		sliderArrondi.setMax(max);
		sliderArrondi.setBlockIncrement(increment);
	}

	// Ajouter une ville à la liste
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

	// Supprimer une ville de la liste
	public void removeCity(City city) {
		listCity.remove(city);
	}

	// Mettre à jour la table des villes
	public void updateTable() {
		cityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty    (cellData.getValue().getNameCity()));
		cityNameColumn.getStyleClass().add("large-cell-text");
		
		cityActionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDeleteBtn()));

		tableCity.setItems(listCity);
	}

	// Synchroniser les valeurs entre le slider et le champ texte
	public void syncValueSliderTextField() {
		if (isInteger(txtArrondi.getText())) {
			sliderArrondi.setValue(Integer.parseInt(txtArrondi.getText()));
		}

		// Récupérer la valeur du slider et arrondir au centième
		double roundedValue = Math.round(sliderArrondi.getValue() * 100.0) / 100.0;

		// Afficher la valeur arrondie dans le champ texte
		txtArrondi.setText(Double.toString(roundedValue));
	}

	// Vérifier si une chaîne est un entier
	public static boolean isInteger(String text) {
		if (text == null || text.isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void launchAnalyse()
	{
		formVille   .setVisible(false);
		formVille   .setManaged(false);
		formHabitant.setVisible(false);
		formHabitant.setManaged(false);
		loadPage    .setVisible(true );
		loadPage    .setVisible(true );

		boolean isDataReceived = true;
		while(isDataReceived)
		{
			String panel =  ""; //Méthode pour le panel 
			
			if(panel != null) {isDataReceived = false;}
		}
	}
}
