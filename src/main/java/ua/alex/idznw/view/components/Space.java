package ua.alex.idznw.view.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ua.alex.idznw.view.model.SelectionModel;

public class Space extends Pane {
	private SelectionModel selectionModel;
	
	private DoubleProperty startSelectionX = new SimpleDoubleProperty(),
						   startSelectionY = new SimpleDoubleProperty(),
						   endSelectionX   = new SimpleDoubleProperty(),
						   endSelectionY   = new SimpleDoubleProperty();
	
	private BooleanProperty visibleSelectionFlag = new SimpleBooleanProperty();
	
	private Rectangle selection = new Rectangle();
	
	private static final EventHandler<MouseEvent> thisClickListener = (e) -> {
		Space tmp = (Space) e.getSource();
		for (int i = 0; i < 2; i++) {
		tmp.startSelectionX.set(e.getX());
		tmp.startSelectionY.set(e.getY());
		tmp.endSelectionX.set(e.getX());
		tmp.endSelectionY.set(e.getY()); }
		tmp.visibleSelectionFlag.set(true);
	};
	
	private static final EventHandler<MouseEvent> thisDragListener = (e) -> {
		Space tmp = (Space) e.getSource();
		tmp.endSelectionX.set(e.getX());
		tmp.endSelectionY.set(e.getY());
	};
	
	private static final EventHandler<MouseEvent> thisReleaseListener = (e) -> {
		Space tmp = (Space) e.getSource();
		tmp.visibleSelectionFlag.set(false);
		for (Node item : tmp.getChildren()) {
			if (item instanceof Component) {
				Component component = (Component) item;
				if ((component.getLayoutX() > tmp.startSelectionX.get() &&
					 component.getLayoutY() > tmp.startSelectionY.get() &&
					 component.getLayoutX() < tmp.endSelectionX.get() &&
					 component.getLayoutY() < tmp.endSelectionY.get()) ||
				    (component.getLayoutX() + component.getPrefWidth()  < tmp.startSelectionX.get() &&
				     component.getLayoutY() + component.getPrefHeight() < tmp.startSelectionY.get() &&
				     component.getLayoutX() + component.getPrefWidth()  < tmp.endSelectionX.get() &&
				     component.getLayoutY() + component.getPrefHeight() < tmp.endSelectionY.get())) {
					//System.out.println("!!");
					tmp.selectionModel.add(component);
				}
			}
		}
	};
	
	public SelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	public void addComponent(Component c) {
		this.getChildren().add(c);
	}
	
	public Space() {
		super();
		
		selection.layoutXProperty().bind(startSelectionX);
		selection.layoutYProperty().bind(startSelectionY);
		selection.widthProperty().bind(endSelectionX.subtract(startSelectionX));
		selection.heightProperty().bind(endSelectionY.subtract(startSelectionY));
		selection.visibleProperty().bind(visibleSelectionFlag);
		selection.getStyleClass().add("selection_component");
		selection.setOpacity(0.6);
		this.getChildren().add(selection);
		
		selectionModel = new SelectionModel(this);
		
		this.setOnMouseClicked(thisClickListener);
		this.setOnMouseDragged(thisDragListener);
		this.setOnMouseReleased(thisReleaseListener);
		
		this.getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> change) {
				while (change.next()) {
					if (change.wasRemoved()) {
						for (Object item : change.getRemoved()) {
							if (!(item instanceof Component)) continue;
							selectionModel.remove((Component) item);
						}
					}
				}
			}
		
		});
	}
}
