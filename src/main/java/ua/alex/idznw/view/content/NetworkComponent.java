package ua.alex.idznw.view.content;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import ua.alex.idznw.view.ComponentContent;
import ua.alex.idznw.view.model.UpdateType;

public class NetworkComponent extends ComponentContent {

	private static final double DEFAULT_WIDTH  = 100;
	private static final double DEFAULT_HEIGHT = 80;
	
	private Label ipText;
	private TextField ipTextField;
	
	private static final EventHandler<MouseEvent> doubleClickToIp = (e) -> {
		if (e.getClickCount() == 2) {
			NetworkComponent nc = (NetworkComponent) ((Node) e.getSource()).getParent();
			nc.setEditIpMode(nc.getView().isSelected());
			e.consume();
		}
	};
	
	public NetworkComponent() {
		super();
		
		this.setPrefWidth(DEFAULT_WIDTH);
		this.setPrefHeight(DEFAULT_HEIGHT);
		
		Ellipse ellipse = new Ellipse();
		ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(2));
		ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(2));
		ellipse.setFill(Color.AZURE);
		ellipse.setStroke(Color.BLACK);
		this.getChildren().add(ellipse);
		
		ipText = new Label("0.0.0.0");
		ipText.layoutXProperty().bind(this.widthProperty() .divide(2).subtract(ipText.widthProperty() .divide(2)));
		ipText.layoutYProperty().bind(this.heightProperty().divide(2).subtract(ipText.heightProperty().divide(2)));
		this.minWidthProperty().bind(ipText.widthProperty().add(10));
		this.minHeightProperty().bind(ipText.heightProperty().add(20));
		ipText.setOnMouseClicked(doubleClickToIp);
		this.getChildren().add(ipText);
		
		ipTextField = new TextField();
		ipTextField.setVisible(false);
		ipTextField.layoutXProperty().bind(this.widthProperty() .divide(2).subtract(ipTextField.widthProperty() .divide(2)));
		ipTextField.layoutYProperty().bind(this.heightProperty().divide(2).subtract(ipTextField.heightProperty().divide(2)));
		this.minWidthProperty().bind(ipTextField.widthProperty().add(10));
		this.minHeightProperty().bind(ipTextField.heightProperty().add(20));
		this.getChildren().add(ipTextField);
	}
	
	public void setEditIpMode(boolean val) {
		if (val) {
			ipTextField.setText(ipText.getText());
			ipText.setVisible(false);
			ipTextField.setVisible(true);
		}
		else {
			ipText.setText(ipTextField.getText());
			ipTextField.setVisible(false);
			ipText.setVisible(true);
		}
	}
	
	public boolean getEditIpMode() {
		return ipTextField.isVisible();
	}

	@Override
	public MenuItem[] getMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UpdateType updateType, Object... data) {
		if (updateType == UpdateType.SELECT) {
			if (getEditIpMode() && ((boolean) data[0]) == false) {
				setEditIpMode(false);
			}
		}
	}

	@Override
	public void setData(String... data) {
		// TODO Auto-generated method stub
		if (data.length == 0) return;
		ipText.setText(data[0]);
	}

	@Override
	public String[] getData() {
		String[] tmp = new String[1];
		tmp[0] = ipText.getText();
		return tmp;
	}
}
