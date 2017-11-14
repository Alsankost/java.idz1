package ua.alex.idznw.view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import ua.alex.idznw.view.model.ComponentsSet;
import ua.alex.idznw.view.model.SelectionModel;

public class Space extends Pane {
	private SelectionModel selectionModel;
	
	private boolean componentFocusFlag = false;

	public static final EventHandler<DragEvent> dragOver = (e) -> {
		Dragboard db = ((DragEvent) e).getDragboard();
		String name = db.getString();
		if (name.length() == 0) return;
		if (name.substring(0, 1).compareTo("+") != 0 ||
			!ComponentsSet.isContains(name.substring(1, name.length()))) return;
		e.acceptTransferModes(TransferMode.ANY);
        /*DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        myTestButton.setEffect(dropShadow);*/
	};	
	
	public static final EventHandler<DragEvent> dragExited = (e) -> {
		Dragboard db = ((DragEvent) e).getDragboard();
		String name = db.getString();
		if (name.length() == 0) return;
		if (name.substring(0, 1).compareTo("+") != 0 ||
			!ComponentsSet.isContains(name.substring(1, name.length()))) return;
		e.acceptTransferModes(TransferMode.ANY);
        //myTestButton.setEffect(null);
        e.consume();
	};	
	
	public static final EventHandler<DragEvent> dragDrop = (e) -> {
		Space space = (Space) ((ScrollPane) e.getSource()).getContent();
		//added = bp.getCenter().getClass();
		Dragboard db = ((DragEvent) e).getDragboard();
		String name = db.getString();
		
		if (name.length() == 0) return;
		if (name.substring(0, 1).compareTo("+") != 0 ||
			!ComponentsSet.isContains(name.substring(1, name.length()))) return;
		
		ComponentContent content = ComponentsSet.createContent(name.substring(1, name.length()));
		if (content == null) return;
		ComponentView temp = new ComponentView(e.getX() - content.getPrefWidth() / 2, e.getY() - content.getPrefHeight() / 2, content);
		space.getChildren().add(temp);
	};	
	
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
	
	private static final EventHandler<MouseEvent> thisClickListener = (e) -> {
		if (e.isDragDetect()) return;
		Space tmp = (Space) e.getSource();
		tmp.selectionModel.clear();
	};
	
	public SelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	public void addComponent(ComponentView c) {
		this.getChildren().add(c);
	}
	
	public Space() {
		super();
		selectionModel = new SelectionModel(this);
		
		this.setOnMousePressed(thisPressListener);
		this.setOnMouseDragged(thisDragListener);
		this.setOnMouseReleased(thisReleaseListener);
		this.setOnMouseClicked(thisClickListener);
		
		this.getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> change) {
				while (change.next()) {
					if (change.wasRemoved()) {
						for (Object item : change.getRemoved()) {
							if (!(item instanceof ComponentView)) continue;
							selectionModel.remove((ComponentView) item);
						}
					}
				}
			}
		
		});
	}
	
	public ConnectionView getConnection(ComponentView c1, ComponentView c2) {
		for (Node item : this.getChildren()) {
			if (item instanceof ConnectionView) {
				ConnectionView temp = (ConnectionView) item;
				if ((temp.getFirstPoint().equals(c1) && temp.getSecondPoint().equals(c2)) ||
					(temp.getFirstPoint().equals(c2) && temp.getSecondPoint().equals(c1))) {
					return temp;
				}
			}
		}
		return null;
	}
	
	public boolean connect(ComponentView first, ComponentView second) {
		if (first.equals(second) || getConnection(first, second) != null) return false;
		
		ConnectionView connection = new ConnectionView(first, second);
		this.getChildren().add(connection);
		connection.toBack();
		return true;
	}
	
	public boolean disconnect(ComponentView first, ComponentView second) {
		if (first.equals(second)) return false;
		ConnectionView temp = getConnection(first, second);
		if (temp == null) return false;
		this.getChildren().remove(temp);
		return true;
	}
	
	public List<ConnectionView> getConnectionsFromComponent(ComponentView component) {
		List<ConnectionView> connections = new ArrayList<ConnectionView>();
		
		for (Node item : this.getChildren()) {
			if (item instanceof ConnectionView) {
				ConnectionView temp = (ConnectionView) item;
				if ((temp.getFirstPoint().equals(component) || temp.getSecondPoint().equals(component))) {
					connections.add(temp);
				}
			}
		}
		return connections;
	}
	
	public void disconnectComponent(ComponentView component) {
		List<ConnectionView> connections = getConnectionsFromComponent(component);
		if (connections.size() == 0) return;
		//Warning!
		this.getChildren().removeAll(connections);
	}
 }
