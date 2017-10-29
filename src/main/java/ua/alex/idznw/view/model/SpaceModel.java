package ua.alex.idznw.view.model;

import javafx.scene.layout.Pane;

public class SpaceModel {
	private SelectionModel selectionModel;
	private Pane container;
	
	public SelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	public Pane getContainer() {
		return container;
	}
	
	public SpaceModel(Pane container) {
		selectionModel = new SelectionModel(this);
		this.container = container;
	}
}
