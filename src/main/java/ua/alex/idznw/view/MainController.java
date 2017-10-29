package ua.alex.idznw.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ua.alex.idznw.view.components.NetworkComponent;

public class MainController implements Initializable {

	@FXML
	private AnchorPane content; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("asd");
		content.getChildren().add(new NetworkComponent(200, 200));
	}
	
}