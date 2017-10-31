package ua.alex.idznw.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.alex.idznw.view.components.Component;
import ua.alex.idznw.view.components.Space;

public class SelectionModel {
	
	private Space space;
	
	private ObservableList<Component> selectionList;
	
	public Space getSpaceModel() {
		return space;
	}
	
	public void add(Component component) {
		selectionList.add(component);
		component.selectionOn();
	}
	
	public void remove(Component component) {
		selectionList.remove(component);
		component.selectionOff();
	}
	
	public void clear() {
		for (Component item : selectionList) {
			item.selectionOff();
		}
		selectionList.clear();
	}

	public SelectionModel(Space spaceModel) {
		this.space = spaceModel;
		selectionList = FXCollections.observableArrayList();
	}
	
}