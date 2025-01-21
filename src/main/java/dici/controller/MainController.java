package dici.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

import dici.classes.City;


public class MainController {

	@FXML
	private Pane                      contenuDynamique; // Pane dynamique pour charger les vues
	@FXML
	private Button                    btnAdd; // Bouton pour ajouter une ville
	@FXML
	private TextField                 txtCity; // Champ de texte pour saisir une ville
	@FXML
	private TextField                 txtArrondi; // Champ de texte pour saisir une ville	
	@FXML
	private Slider                    sliderArrondi; 

	@FXML
    private RadioButton radioPlus10;

    @FXML
    private RadioButton radioPlus100;

    @FXML
    private RadioButton radioPlus1000;

    private ToggleGroup toggleGroup;

	@FXML
	private TableView<City>           tableCity; // TableView pour afficher les villes
	@FXML
	private TableColumn<City, String> cityNameColumn;
	@FXML
	private TableColumn<City, Button> cityActionColumn;

	private ObservableList<City>      listCity; // Liste des villes

	@FXML
	public void initialize() {
		listCity = FXCollections.observableArrayList();
	}


	// Afficher la vue pour gérer les villes
	public void afficherVueVille     () 
	{ 
		loadView("/dici/fxml/fenetre_ville.fxml"   );
		tableCity.setItems(listCity);
	}

	// Afficher la vue pour gérer le nombre d'habitants
	public void afficherVueNbHabitant() 
	{ 
		loadView("/dici/fxml/fenetre_habitant.fxml");

		this.setupListeners();
		this.setToogleGroup();
	}

	// Charger une vue FXML dans le panneau dynamique
	public void loadView(String fxml) {
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

	public void setupListeners() {
        if (sliderArrondi != null) {
            sliderArrondi.valueProperty().addListener((observable, oldValue, newValue) -> {
                syncValueSliderTextField();
            });
        }
    }

	public void setToogleGroup()
	{
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

	  // Méthode pour gérer les clics
	private void handleRadioButtonClick(String text) {
        // Ajoutez ici votre logique pour chaque bouton
		double increment = 1;
        switch (text) {
            case "+10":
				increment = 10;
                break;
            case "+100":
                increment = 100;
                break;
            case "+1000":
				increment = 1000;
                break;
            default:
                increment = 10;
                break;
        }

		sliderArrondi.setBlockIncrement(increment);
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
		cityNameColumn.getStyleClass().add("large-cell-text");
		cityActionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDeleteBtn()));

		tableCity.setItems(listCity);
	}

	// Synchroniser les valeurs d'un curseur et d'un champ de texte (fonctionalité à implémenter si nécessaire)
	public void syncValueSliderTextField() {
		if (isInteger(txtArrondi.getText())) 
			sliderArrondi.setValue(Integer.parseInt(txtArrondi.getText()));
		
	
		// Récupérer la valeur du slider et arrondir au centième
		double roundedValue = Math.round(sliderArrondi.getValue() * 100.0) / 100.0;
		
		// Afficher la valeur arrondie dans le champ texte
		txtArrondi.setText(Double.toString(roundedValue));
	}
	

	public static boolean isInteger(String text) {
		if (text == null || text.isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(text);
			return true; // La conversion a réussi, donc c'est un entier.
		} catch (NumberFormatException e) {
			return false; // La conversion a échoué, donc ce n'est pas un entier.
		}
	}
	
}


