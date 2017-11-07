package ua.alex.idznw.view.content;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import ua.alex.idznw.view.model.ComponentContent;

public class NetworkComponent extends ComponentContent {

	public NetworkComponent() {
		super();
		
		Ellipse ellipse = new Ellipse();
		ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(2));
		ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(2));
		ellipse.setFill(Color.AZURE);
		ellipse.setStroke(Color.BLACK);
		this.getChildren().add(ellipse);
	}
}