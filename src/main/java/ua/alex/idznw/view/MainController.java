package ua.alex.idznw.view;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ua.alex.idznw.Start;
import ua.alex.idznw.model.xml.XmlDataManager;
import ua.alex.idznw.view.model.Tool;

public class MainController implements Initializable {
	
	@FXML
	private TabPane content;
	
	@FXML
	private VBox componentSet;
	
	@FXML
	private BorderPane root;
	
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
	
	//==Choosers
	private FileChooser fileChooser = new FileChooser();
	
	//==Action listeners
	public void menuAction_New() {
		ScrollPane sp = new ScrollPane();
		sp.setContent(new Space());
		
		sp.setOnDragOver(Space.dragOver);
		sp.setOnDragEntered(Space.dragExited);
		sp.setOnDragDropped(Space.dragDrop);
		
		Tab tab = new Tab("new");
		tab.setClosable(true);
		
		tab.setContent(sp);
		content.getTabs().add(tab);
	}
	
	public void menuAction_Save() {
		fileChooser.setTitle("Save space");
		
		File file = fileChooser.showSaveDialog(Start.getPrimaryStage());
		if (file == null) {
			return;
		}
		
		try {
			XmlDataManager.save(getCurrentSpace(), file);
			this.getCurrentTab().setText(file.getName());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void menuAction_Open() {
		fileChooser.setTitle("Open space");
		
		File file = fileChooser.showOpenDialog(Start.getPrimaryStage());
		if (file == null) {
			return;
		}
		
		try {
			Space space = XmlDataManager.load(file);
			ScrollPane sp = new ScrollPane();
			sp.setContent(space);
			
			sp.setOnDragOver(Space.dragOver);
			sp.setOnDragEntered(Space.dragExited);
			sp.setOnDragDropped(Space.dragDrop);
			
			Tab tab = new Tab(file.getName());
			tab.setClosable(true);
			
			tab.setContent(sp);
			content.getTabs().add(tab);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//==System
	public Tab getCurrentTab() {
		return content.getSelectionModel().getSelectedItem();
	}
	
	public Space getCurrentSpace() {
		return (Space) ( (ScrollPane) getCurrentTab().getContent() ).getContent();
	}
	
	public void addToComponentSet(Node node) {
		componentSet.getChildren().add(node);
	}
	
	private EventHandler<ActionEvent> toolAction = (e) -> {
		((Space) ((ScrollPane) content.getSelectionModel().getSelectedItem().getContent()).getContent()).getSelectionModel().clear();
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
		
		content.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
		/*
		//System.out.println("asd");
		Tab tab = new Tab("Test");
		s = new Space();
		*/
		//ComponentView c2 = new ComponentView(200, 200, new NetworkComponent(true));
		//s.addComponent(c2);
		/*
		AnchorPane ap = new AnchorPane();
		ap.getStyleClass().add("border_test");
		ap.setMinHeight(100);
		ap.setMinWidth(100);
		s.getChildren().add(ap);
		tab.setContent(s);
		//tab.getTabPane().getTabs().add(tab);
		content.getTabs().add(tab);*/
		
		//c1.setMinWidth(200);
	}
}