package dici.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dici.controllers.MainController;
import dici.models.api.APITools;
import dici.models.dao.DatabaseService;
import dici.views.components.City;

public class MainView {

	private MainController controller;

	private ObservableList<City> listCity;

	@FXML private GridPane formVille;
	@FXML private VBox     formHabitant;
	@FXML private FlowPane loadPage;

	@FXML private Button btnAdd;
	@FXML private TextField txtCity;
	@FXML private TextField txtArrondi;
	@FXML private Slider sliderArrondi;

	@FXML private RadioButton radioPlus10;
	@FXML private RadioButton radioPlus100;
	@FXML private RadioButton radioPlus1000;

	@FXML private TableView<City> tableCity;
	@FXML private TableColumn<City, String> cityNameColumn;
	@FXML private TableColumn<City, Button> cityActionColumn;

	public MainView ( MainController controller )
	{
		this.controller = controller;
		this.listCity = FXCollections.observableArrayList();
		Application.launch ( AppViewTools.class );

	}

	@FXML
	public void initialize() {
		setupTable();
        setupSlider();
        setupRadioButtons();
	}
	
    public void afficherVueVille() {
        afficherVue("ville");
    }

    public void afficherVueNbHabitant() {
        afficherVue("habitant");
    }

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


	public void updateTable() 
	{
		cityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameCity()));
		cityActionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDeleteBtn()));
		tableCity.setItems(this.listCity);
	}

	private void setupTable() {
		cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameCity"));
		cityActionColumn.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
		updateTable();
	}

	private void setupSlider() {
		sliderArrondi.valueProperty().addListener((observable, oldValue, newValue) -> syncValueSliderTextField());
	}

	private void setupRadioButtons() {
		ToggleGroup toggleGroup = new ToggleGroup();
		radioPlus10.setToggleGroup(toggleGroup);
		radioPlus100.setToggleGroup(toggleGroup);
		radioPlus1000.setToggleGroup(toggleGroup);

		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				RadioButton selectedRadio = (RadioButton) newValue;
				this.handleRadioButtonClick(selectedRadio.getText());
			}
		});
	}


	public void addVille() {
	//	this.showPopUp();

		String cityName = this.txtCity.getText();
		if (cityName == null || cityName.trim().isEmpty()) {
			System.out.println("Veuillez entrer un nom de ville valide.");
			return;
		}

		DatabaseService database = new DatabaseService();
		Map<String, List<String>> citiesInfo = database.getCityInfoByNames(new ArrayList<>(List.of(cityName)));
		List<String> cityInfo = citiesInfo.get(cityName);
		String codeVille = cityInfo.getLast();

		database.updatePriceInDatabase(Integer.parseInt(codeVille), APITools.getPriceM2(codeVille));

		City newItem = new City(cityName.trim());
		newItem.getDeleteBtn().setOnAction(e -> removeCity(newItem));
		listCity.add(newItem);

		this.updateTable();
		this.txtCity.clear();
	}

	public void afficherPopUp()
	{
		Popup popUp = new Popup();

		Label label = new Label("Il n'y a pas de données pour cette ville");
		label.setStyle    ("-fx-background-color: white;");
		label.setMinWidth (80);
		label.setMinHeight(50);
		popUp.getContent().add(label);

		//popUp.show(this);
	}

	public void removeCity(City city) {
		listCity.remove(city);
		this.updateTable();
	}

	public void handleRadioButtonClick(String text) {
		double increment;
		int max;
		switch (text) {
			case "+10":
				increment = 10;
				max = 100;
				break;
			case "+100":
				increment = 100;
				max = 1000;
				break;
			case "+1000":
				increment = 1000;
				max = 10000;
				break;
			default:
				increment = 10;
				max = 100;
				break;
		}
		this.sliderArrondi.setMax(max);
		this.sliderArrondi.setBlockIncrement(increment);
	}

	public void launchAnalyse() {
		formVille   .setVisible(false);
		formVille   .setManaged(false);
		formHabitant.setVisible(false);
		formHabitant.setManaged(false);
		loadPage    .setVisible(true );
		loadPage    .setVisible(true );

		// MainController.get().setListCity(this.listCity);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/analyseData.fxml"));
			Parent root = loader.load();
			
			Stage nouvelleStage = new Stage();
			nouvelleStage.setTitle("Analyse");
			nouvelleStage.setScene(new Scene(root));
			
			// Charger l'icône personnalisée
			Image appIcon = new Image(getClass().getResourceAsStream("/images/logo.png"));
			nouvelleStage.getIcons().add(appIcon);
			
			nouvelleStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

}
