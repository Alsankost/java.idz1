package ua.alex.idznw.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class NetworkComponent extends Component {

	public NetworkComponent(double x, double y) {
		super(x, y);
		
		Ellipse ellipse = new Ellipse(1, y, y, y);
		ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(2));
		ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(2));
		ellipse.setFill(Color.AZURE);
		ellipse.setStroke(Color.BLACK);
		this.getChildren().add(ellipse);
	}

}
