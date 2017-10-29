package ua.alex.idznw.view.model;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ua.alex.idznw.view.components.Component;

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
		
		container.getChildren().addListener(new ListChangeListener<Node>() {
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
