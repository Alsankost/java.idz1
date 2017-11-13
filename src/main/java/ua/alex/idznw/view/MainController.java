package ua.alex.idznw.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import ua.alex.idznw.view.model.Tool;

public class MainController implements Initializable {
	
	@FXML
	private TabPane content;
	
	@FXML
	private VBox componentSet;
	
	//==Tools:
	@FXML
	private Button moveToolButton;
	
	@FXML
	private Button connectToolButton;
	
	@FXML
	private Button disconnectToolButton;
	
	private Map<Tool, Button> toolButtons = new HashMap<Tool, Button>();
	
	public Tool getCurrentTool() {
		for (Tool key : toolButtons.keySet()) {
			if (toolButtons.get(key).isDisable()) {
				return key;
			}
		}
		return null;
	}
	
	@FXML
	private void test() {
	}
	
	public void addToComponentSet(Node node) {
		componentSet.getChildren().add(node);
	}
	
	public Space getCurrentSpace() {
		return (Space) content.getSelectionModel().getSelectedItem().getContent();
	}
	
	private EventHandler<ActionEvent> toolAction = (e) -> {
		((Space) content.getSelectionModel().getSelectedItem().getContent()).getSelectionModel().clear();
		for (Button item : toolButtons.values()) {
			if (item.equals(e.getSource())) {
				item.setDisable(true);
			}
			else {
				item.setDisable(false);
			}	
		}
	};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.moveToolButton.setDisable(true);
		toolButtons.put(Tool.EDIT,       this.moveToolButton);
		toolButtons.put(Tool.CONNECT,    this.connectToolButton);
		toolButtons.put(Tool.DISCONNECT, this.disconnectToolButton);
		
		for (Button item : toolButtons.values()) {
			item.setOnAction(toolAction);
		}
		
		//System.out.println("asd");
		Tab tab = new Tab("Test");
		Space s = new Space();
		
		//ComponentView c2 = new ComponentView(200, 200, new NetworkComponent(true));
		//s.addComponent(c2);
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