package ua.alex.idznw.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ua.alex.idznw.view.components.Component;
import ua.alex.idznw.view.components.NetworkComponent;
import ua.alex.idznw.view.components.Space;

public class MainController implements Initializable {

	Component c1 = new NetworkComponent(200, 200);
	
	@FXML
	private TabPane content; 
	
	@FXML
	private void test() {
		c1.setPrefWidth(200);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("asd");
		Tab tab = new Tab("Test");
		Space s = new Space();
		
		Component c2 = new NetworkComponent(400, 400);
		s.addComponent(c1);
		s.addComponent(c2);
		/*
		AnchorPane ap = new AnchorPane();
		ap.getStyleClass().add("border_test");
		ap.setMinHeight(100);
		ap.setMinWidth(100);
		s.getChildren().add(ap);*/
		tab.setContent(s);
		//tab.getTabPane().getTabs().add(tab);
		content.getTabs().add(tab);
		
		//c1.setMinWidth(200);
	}
}