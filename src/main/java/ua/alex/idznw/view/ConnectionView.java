package ua.alex.idznw.view;

import javafx.scene.shape.Line;

public class ConnectionView extends Line {
	private ComponentView firstPoint, secondPoint;
	
	public ComponentView getFirstPoint() {
		return firstPoint;
	}
	
	public ComponentView getSecondPoint() {
		return secondPoint;
	}
	
	public ConnectionView(ComponentView fp, ComponentView sp) {
		this.firstPoint = fp;
		this.secondPoint = sp;
		
		this.startXProperty().bind(this.firstPoint.layoutXProperty().add(this.firstPoint.widthProperty().divide(2)));
		this.startYProperty().bind(this.firstPoint.layoutYProperty().add(this.firstPoint.heightProperty().divide(2)));
		this.endXProperty().bind(this.secondPoint.layoutXProperty().add(this.secondPoint.widthProperty().divide(2)));
		this.endYProperty().bind(this.secondPoint.layoutYProperty().add(this.secondPoint.heightProperty().divide(2)));
		
		this.toBack();
	}
}
