package ua.alex.idznw.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import ua.alex.idznw.Start;
import ua.alex.idznw.view.ComponentView;
import ua.alex.idznw.view.Space;

public class SelectionModel {
	
	private Space space;
	
	private ObservableList<ComponentView> selectionList;
	
	private DoubleProperty startSelectionX = new SimpleDoubleProperty(),
			   			   startSelectionY = new SimpleDoubleProperty(),
			   			   endSelectionX   = new SimpleDoubleProperty(),
			   			   endSelectionY   = new SimpleDoubleProperty();
	
	private BooleanProperty visibleSelectionFlag = new SimpleBooleanProperty();
	
	private Rectangle selection = new Rectangle();
	
	private double firstX = 0, firstY = 0;
	
	public Space getSpaceModel() {
		return space;
	}
	
	public void selectOne(ComponentView component) {
		/*for (ComponentView item : selectionList) {
			if (!item.equals(component)) item.selectionOff();
		}*/
		clear();
		add(component);
	}
	
	public void add(ComponentView component) {
		if (selectionList.contains(component)) return;
		selectionList.add(component);
		component.selectionOn();
	}
	
	public void remove(ComponentView component) {
		if (!selectionList.contains(component)) return;
		selectionList.remove(component);
		component.selectionOff();
	}
	
	public void clear() {
		for (ComponentView item : selectionList) {
			item.selectionOff();
		}
		selectionList.clear();
	}
	
	public void moveComponents(double sx, double sy, double x, double y) {
		if (Start.getMainContoller().getCurrentTool() != Tool.EDIT) return;
		for (ComponentView item : selectionList) {
			item.setLayoutX(item.getLayoutX() - sx + x);
			item.setLayoutY(item.getLayoutY() - sy + y);
		}
	}
	
	public void beginSelection(double x, double y) {
		if (space.isComponentFocus() || Start.getMainContoller().getCurrentTool() != Tool.EDIT) return;
		
		firstX = x;
		firstY = y;
		startSelectionX.set(x);
		startSelectionY.set(y);
		endSelectionX.set(x);
		endSelectionY.set(y);
		visibleSelectionFlag.set(true);
	}
	
	public void continueSelection(double x, double y) {
		if (!this.visibleSelectionFlag.get()) return;
		
		double x1 = firstX,
			   y1 = firstY,
			   x2 = x,
			   y2 = y;
		
		if (x1 > x2) {
			x1 = x;
			x2 = firstX;
		}
		
		if (y1 > y2) {
			y1 = y;
			y2 = firstY;
		}
		
		startSelectionX.set(x1);
		startSelectionY.set(y1);
		endSelectionX.set(x2);
		endSelectionY.set(y2);
		
		for (Node item : space.getChildren()) {
			if (item instanceof ComponentView) {
				ComponentView component = (ComponentView) item;
				boolean selectFlag = false;
				for (int a = 0; a < 2 && !selectFlag; a++) {
					for (int b = 0; b < 2 && !selectFlag; b++) {
						double cx = component.getLayoutX() + component.getPrefWidth()  * a,
							   cy = component.getLayoutY() + component.getPrefHeight() * b;
						if (x1 < cx && x2 > cx && y1 < cy && y2 > cy) {
							selectFlag = true;
						}
					}
				}
				if (selectFlag) {
					add(component);
				}
				else {
					remove(component);
				}
			}
		}
	}
	
	public void endSelection() {
		visibleSelectionFlag.set(false);
	}
	
	public boolean isSelected(ComponentView component) {
		return selectionList.contains(component);
	}

	public ComponentView getOne() {
		return selectionList.get(0);
	}
	
	public SelectionModel(Space space) {
		this.space = space;
		
		selection.xProperty().bind(startSelectionX);
		selection.yProperty().bind(startSelectionY);
		selection.widthProperty().bind(endSelectionX.subtract(startSelectionX));
		selection.heightProperty().bind(endSelectionY.subtract(startSelectionY));
		selection.visibleProperty().bind(visibleSelectionFlag);
		visibleSelectionFlag.set(false);
		selection.getStyleClass().add("selection_component");
		selection.setOpacity(0.6);
		space.getChildren().add(selection);
		
		selectionList = FXCollections.observableArrayList();
	}
	
}