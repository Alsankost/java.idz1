package ua.alex.idznw.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ua.alex.idznw.view.model.ComponentContent;
import ua.alex.idznw.view.model.SelectionModel;

public class ComponentView extends Pane {
	public static final double DEFAULT_WIDTH  = 100,
							   DEFAULT_HEIGHT = 100,
							   MIN_WIDTH      = 60,
							   MIN_HEIGHT     = 60,
							   
							   RESIZE_BLOCK_WIDTH  = 8,
							   RESIZE_BLOCK_HEIGHT = 8;
							   
	public static final String SELECTION_STYPE = "selection_component"; 
	
	private double startMoveX = 0, startMoveY = 0;
	
	private static final EventHandler<MouseEvent> resizeBlockDragListener = (e) -> {
		ComponentView block = (ComponentView) ((Rectangle) e.getSource()).getParent();
		
		SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
		selectionModel.clear();
		selectionModel.add(block);
		
		double dx = e.getX();
		double dy = e.getY();
		
		block.setSize(dx, dy);
	};
	
	private static final EventHandler<MouseEvent> thisEnteredListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null) {
			( (Space) block.getParent() ).setComponentFocus(true);
		}
	};
	
	private static final EventHandler<MouseEvent> thisExitedListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null) {
			( (Space) block.getParent() ).setComponentFocus(false);
		}
	};
	
	private static final EventHandler<MouseEvent> thisClickListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null &&
			Math.abs(block.startMoveX - e.getX()) < 1 &&
			Math.abs(block.startMoveY - e.getY()) < 1) {
			SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
			selectionModel.clear();
			selectionModel.add(block);
		}
	};
	
	private static final EventHandler<MouseEvent> thisPressListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		block.startMoveX = e.getX();
		block.startMoveY = e.getY();
	};
	
	private static final EventHandler<MouseEvent> thisDragListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		
		if (e.getX() > ComponentView.RESIZE_BLOCK_WIDTH &&
			e.getX() < block.getPrefWidth() - ComponentView.RESIZE_BLOCK_WIDTH &&
			e.getY() > ComponentView.RESIZE_BLOCK_HEIGHT &&
			e.getY() < block.getPrefHeight() - ComponentView.RESIZE_BLOCK_HEIGHT) {
			SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
			if (block.isSelected()) {
				selectionModel.moveComponents(block.startMoveX, block.startMoveY, e.getX(), e.getY());
			}
			else {
				selectionModel.clear();
				selectionModel.add(block);
			}
		}
	};
	
	public ComponentView(double x, double y, ComponentContent content) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefWidth(DEFAULT_WIDTH);
		this.setPrefHeight(DEFAULT_HEIGHT);
				
		Rectangle resizeBlock = new Rectangle();
		
		content.setLayoutX(RESIZE_BLOCK_WIDTH);
		content.setLayoutY(RESIZE_BLOCK_HEIGHT);
		content.prefWidthProperty().bind(prefWidthProperty().subtract(RESIZE_BLOCK_WIDTH * 2));
		content.prefHeightProperty().bind(prefHeightProperty().subtract(RESIZE_BLOCK_HEIGHT * 2));
		this.getChildren().add(content);
		
		this.minWidthProperty().bind(content.minWidthProperty().add(RESIZE_BLOCK_WIDTH * 2));
		this.minHeightProperty().bind(content.minHeightProperty().add(RESIZE_BLOCK_HEIGHT * 2));
		
		resizeBlock.xProperty().bind(this.widthProperty().subtract(RESIZE_BLOCK_WIDTH));
		resizeBlock.yProperty().bind(this.heightProperty().subtract(RESIZE_BLOCK_HEIGHT));
		resizeBlock.setWidth(RESIZE_BLOCK_WIDTH);
		resizeBlock.setHeight(RESIZE_BLOCK_HEIGHT);
		
		this.getChildren().add(resizeBlock);
		this.setOnMouseEntered(thisEnteredListener);
		this.setOnMouseExited(thisExitedListener);
		this.setOnMouseClicked(thisClickListener);
		this.setOnMousePressed(thisPressListener);
		this.setOnMouseDragged(thisDragListener);
		
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
		this.applyCss();
	}
	
	public void selectionOff() {
		this.getStyleClass().remove(SELECTION_STYPE);
		this.applyCss();
	}
	
	public boolean isSelected() {
		return ( (Space) this.getParent() ).getSelectionModel().isSelected(this);
	}
}
