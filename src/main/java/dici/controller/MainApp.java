package dici.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dici\\fxml\\fenetre_principal.fxml"));
        Parent root = loader.load();

        // Créer une scène
        Scene scene = new Scene(root);

        // Configurer la fenêtre principale
        primaryStage.setTitle("Fenêtre principale");
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
