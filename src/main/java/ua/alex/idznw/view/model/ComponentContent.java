package ua.alex.idznw.view.model;

import javafx.scene.layout.Pane;

public abstract class ComponentContent extends Pane {
	public static final double DEFAULT_MIN_WIDTH = 50;
	public static final double DEFAULT_MIN_HEIGHT = 50;
	
	public ComponentContent() {
		this.setMinWidth(DEFAULT_MIN_WIDTH);
		this.setMinHeight(DEFAULT_MIN_HEIGHT);
	}
}
