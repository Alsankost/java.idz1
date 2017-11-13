package ua.alex.idznw.view.content;

import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import ua.alex.idznw.view.ComponentContent;
import ua.alex.idznw.view.model.UpdateType;

public class RouterComponent extends ComponentContent {
	
	private static final double DEFAULT_WIDTH  = 80;
	private static final double DEFAULT_HEIGHT = 80;
	
	private static Double[] polygonPoints = new Double[6];
	
	static {
		polygonPoints[0] = -1.0;
		polygonPoints[1] = -1.0;
		
		polygonPoints[2] = 1.0;
		polygonPoints[3] = 0.0;
		
		polygonPoints[4] = -1.0;
		polygonPoints[5] =  1.0;
	}
	
	public RouterComponent() {
		super();
		
		this.setMinWidth(DEFAULT_WIDTH);
		this.setMinHeight(DEFAULT_HEIGHT);
		this.setMaxWidth(DEFAULT_WIDTH);
		this.setMaxHeight(DEFAULT_HEIGHT);
		this.setPrefWidth(DEFAULT_WIDTH);
		this.setPrefHeight(DEFAULT_HEIGHT);
		
		Ellipse ellipse = new Ellipse();
		ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(2));
		ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(2));
		ellipse.setFill(Color.BLUE);
		ellipse.setStroke(Color.BLACK);
		this.getChildren().add(ellipse);
		
		Polygon p1 = new Polygon();
		p1.getPoints().addAll(polygonPoints);
		p1.setLayoutX(DEFAULT_WIDTH  - (DEFAULT_WIDTH  / 5));
		p1.setLayoutY(DEFAULT_HEIGHT / 2);
		p1.setScaleX(8);
		p1.setScaleY(8);
		p1.setFill(Color.WHITE);
		this.getChildren().add(p1);
		
		Polygon p2 = new Polygon();
		p2.getPoints().addAll(polygonPoints);
		p2.setRotate(90);
		p2.setLayoutX(DEFAULT_WIDTH  / 2);
		p2.setLayoutY(DEFAULT_HEIGHT - (DEFAULT_HEIGHT  / 5));
		p2.setScaleX(8);
		p2.setScaleY(8);
		p2.setFill(Color.WHITE);
		this.getChildren().add(p2);
		
		Polygon p3 = new Polygon();
		p3.getPoints().addAll(polygonPoints);
		p3.setRotate(180);
		p3.setLayoutX(DEFAULT_WIDTH  / 5);
		p3.setLayoutY(DEFAULT_HEIGHT / 2);
		p3.setScaleX(8);
		p3.setScaleY(8);
		p3.setFill(Color.WHITE);
		this.getChildren().add(p3);
		
		Polygon p4 = new Polygon();
		p4.getPoints().addAll(polygonPoints);
		p4.setRotate(270);
		p4.setLayoutX(DEFAULT_WIDTH  / 2);
		p4.setLayoutY(DEFAULT_HEIGHT / 5);
		p4.setScaleX(8);
		p4.setScaleY(8);
		p4.setFill(Color.WHITE);
		this.getChildren().add(p4);
	}
	
	public boolean isResizableContent() {
		return false;
	}

	@Override
	public MenuItem[] getMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UpdateType updateType, Object... data) {
		// TODO Auto-generated method stub
	}
}