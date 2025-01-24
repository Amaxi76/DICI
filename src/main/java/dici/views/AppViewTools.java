package dici.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppViewTools extends Application 
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main.fxml"));
        Parent root = loader.load();

        // Créer une scène
        Scene scene = new Scene(root);
    
        // Configurer la fenêtre principale
        primaryStage.setTitle("Fenêtre principale");
        primaryStage.setScene(scene);
        
        // Charger l'icône personnalisée
        Image appIcon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        primaryStage.getIcons().add(appIcon);
        
        // Afficher la fenêtre
        primaryStage.show();
    }
}
