package ua.alex.idznw.view.components;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class Component extends Pane {
	public static final double DEFAULT_WIDTH  = 100,
							   DEFAULT_HEIGHT = 100,
							   MIN_WIDTH      = 60,
							   MIN_HEIGHT     = 60,
							   
							   RESIZE_BLOCK_WIDTH  = 8,
							   RESIZE_BLOCK_HEIGHT = 8;
							   
	public static final String SELECTION_STYPE = "selection_component"; 
	
	private static final EventHandler<MouseEvent> resizeBlockDragListener = (e) -> {
		Component block = (Component) ((Rectangle) e.getSource()).getParent();
		
		double dx = e.getX();
		double dy = e.getY();
		
		//System.out.println(block);
		
		//dx = (dx < MIN_WIDTH )? MIN_WIDTH:dx;
		//dy = (dy < MIN_HEIGHT)?MIN_HEIGHT:dy;
		
		block.setSize(dx, dy);
	};
	
	public Component(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setMinWidth(MIN_WIDTH);
		this.setMinHeight(MIN_HEIGHT);
		this.setPrefWidth(DEFAULT_WIDTH);
		this.setPrefHeight(DEFAULT_HEIGHT);
		
		//this.getStyleClass().add("border_test");
				
		Rectangle resizeBlock = new Rectangle();
		
		resizeBlock.xProperty().bind(this.widthProperty().subtract(RESIZE_BLOCK_WIDTH));
		resizeBlock.yProperty().bind(this.heightProperty().subtract(RESIZE_BLOCK_HEIGHT));
		resizeBlock.setWidth(RESIZE_BLOCK_WIDTH);
		resizeBlock.setHeight(RESIZE_BLOCK_HEIGHT);
		
		this.getChildren().add(resizeBlock);
		
		resizeBlock.setOnMouseDragged(resizeBlockDragListener);
	}
	
	public void setPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
	
	public void setSize(double w, double h) {
		this.setPrefWidth(w);
		this.setPrefHeight(h);
	}
	
	public void selectionOn() {
		this.getStyleClass().add(SELECTION_STYPE);
		System.out.println("a");
		this.applyCss();
	}
	
	public void selectionOff() {
		this.getStyleClass().remove(SELECTION_STYPE);
		System.out.println("b");
		this.applyCss();
	}
}
