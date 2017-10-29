package ua.alex.idznw.view.components;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public abstract class Component extends AnchorPane {
	public static final double DEFAULT_WIDTH  = 100,
							   DEFAULT_HEIGHT = 100,
							   MIN_WIDTH      = 60,
							   MIN_HEIGHT     = 60,
							   
							   RESIZE_BLOCK_WIDTH  = 8,
							   RESIZE_BLOCK_HEIGHT = 8;
	
	private static final EventHandler<MouseEvent> resizeBlockDragListener = (e) -> {
		Component block = (Component) ((Node) e.getSource()).getParent();
		
		double dx = e.getX();
		double dy = e.getY();
		
		dx = (dx < MIN_WIDTH )? MIN_WIDTH:dx;
		dy = (dy < MIN_HEIGHT)?MIN_HEIGHT:dy;
		
		block.setSize(dx, dy);
	};
	
	private static final EventHandler<MouseEvent> thisClickListener = (e) -> {
		Component block = (Component) ((Node) e.getSource()).getParent();
		
		double dx = e.getX();
		double dy = e.getY();
		
		dx = (dx < MIN_WIDTH )? MIN_WIDTH:dx;
		dy = (dy < MIN_HEIGHT)?MIN_HEIGHT:dy;
		
		block.setSize(dx, dy);
	};
	
	private static final EventHandler<MouseEvent> thisDragListener = (e) -> {
		Component block = (Component) ((Node) e.getSource()).getParent();
		
		double dx = e.getX();
		double dy = e.getY();
		
		dx = (dx < MIN_WIDTH )? MIN_WIDTH:dx;
		dy = (dy < MIN_HEIGHT)?MIN_HEIGHT:dy;
		
		block.setSize(dx, dy);
	};

	private Rectangle resizeBlock;
	
	public Component(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setWidth(DEFAULT_WIDTH);
		this.setHeight(DEFAULT_HEIGHT);
		
		this.getStyleClass().add("border_test");
				
		resizeBlock = new Rectangle();
		resizeBlock.xProperty().bind(this.widthProperty().subtract(RESIZE_BLOCK_WIDTH));
		resizeBlock.yProperty().bind(this.heightProperty().subtract(RESIZE_BLOCK_HEIGHT));
		resizeBlock.setWidth(RESIZE_BLOCK_WIDTH);
		resizeBlock.setHeight(RESIZE_BLOCK_HEIGHT);
		
		this.getChildren().add(resizeBlock);
		
		resizeBlock.setOnMouseDragged(resizeBlockDragListener);
		
		this.setOnMouseClicked(thisClickListener);
		this.setOnMouseDragged(thisDragListener);
	}
	
	public void setPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
	
	public void setSize(double w, double h) {
		this.setWidth(w);
		this.setHeight(h);
	}
}
