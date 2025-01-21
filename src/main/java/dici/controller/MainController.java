package dici.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;

import dici.classes.City;

public class MainController {

    @FXML
    private Pane contenuDynamique; // Pane dynamique pour charger les vues

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

		tableCity     = new TableView<>();
		radioPlus10   = new RadioButton();
		radioPlus100  = new RadioButton();
		radioPlus1000 = new RadioButton();
		sliderArrondi = new Slider();
	

    }

    // Afficher la vue pour gérer les villes
    public void afficherVueVille() {
        loadView("/dici/fxml/fenetre_ville.fxml", () -> {
            tableCity.setItems(listCity);
        });
    }

    // Afficher la vue pour gérer le nombre d'habitants
    public void afficherVueNbHabitant() {
        loadView("/dici/fxml/fenetre_habitant.fxml", () -> {

            this.setupListeners();
            this.setToggleGroup();
        });
    }

    // Charger une vue FXML dans le panneau dynamique
    public void loadView(String fxml, Runnable onLoaded) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane nouvelleVue = loader.load();

            // Adapter la taille de la nouvelle vue au panneau dynamique
            nouvelleVue.prefWidthProperty().bind(contenuDynamique.widthProperty());
            nouvelleVue.prefHeightProperty().bind(contenuDynamique.heightProperty());

            // Nettoyer et ajouter la nouvelle vue
            contenuDynamique.getChildren().clear();
            contenuDynamique.getChildren().add(nouvelleVue);

            // Exécuter l'action de rappel après le chargement
            if (onLoaded != null) {
                Platform.runLater(onLoaded);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
		System.out.println("je suis a");
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
    private void handleRadioButtonClick(String text) {
        double increment;
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
        cityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameCity()));
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
}
