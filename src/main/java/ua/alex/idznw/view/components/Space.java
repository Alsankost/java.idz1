package ua.alex.idznw.view.components;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ua.alex.idznw.view.model.SelectionModel;

public class Space extends Pane {
	private SelectionModel selectionModel;
	
	private boolean componentFocusFlag = false;
	
	public void setComponentFocus(boolean flag) {
		componentFocusFlag = flag;
	}
	
	public boolean isComponentFocus() {
		return componentFocusFlag;
	}
	
	private static final EventHandler<MouseEvent> thisPressListener = (e) -> {
		Space tmp = (Space) e.getSource();
		tmp.selectionModel.beginSelection(e.getX(), e.getY());
	};
	
	private static final EventHandler<MouseEvent> thisDragListener = (e) -> {
		Space tmp = (Space) e.getSource();
		tmp.selectionModel.continueSelection(e.getX(), e.getY());
	};
	
	private static final EventHandler<MouseEvent> thisReleaseListener = (e) -> {
		Space tmp = (Space) e.getSource();
		tmp.selectionModel.endSelection();
	};
	
	public SelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	public void addComponent(Component c) {
		this.getChildren().add(c);
	}
	
	public Space() {
		super();
		
		selectionModel = new SelectionModel(this);
		
		this.setOnMousePressed(thisPressListener);
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
