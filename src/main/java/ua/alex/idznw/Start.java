package ua.alex.idznw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.alex.idznw.view.MainController;

public class Start extends Application {

	private static MainController mainController;
	
	public MainController getMainContoller() {
		return mainController;
	}
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(Start.class.getResource("view/fxml/MainForm.fxml"));
		Parent root = loader.load();
		mainController = loader.getController();
		
		Scene scene = new Scene(root, 1000, 900);
		scene.getStylesheets().add(getClass().getResource("view/css/style.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
