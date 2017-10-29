package ua.alex.idznw.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import ua.alex.idznw.view.components.Component;

public class SelectionModel {
	
	private SpaceModel spaceModel;
	
	private ObservableList<Component> selectionList;
	
	public SpaceModel getSpaceModel() {
		return spaceModel;
	}

	public SelectionModel(SpaceModel spaceModel) {
		this.spaceModel = spaceModel;
		selectionList = FXCollections.observableArrayList();
	}
	
}