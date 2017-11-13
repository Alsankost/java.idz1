package ua.alex.idznw.view;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import ua.alex.idznw.view.model.UpdateType;

public abstract class ComponentContent extends Pane {
	public static final double DEFAULT_MIN_WIDTH = 50;
	public static final double DEFAULT_MIN_HEIGHT = 50;
	
	public ComponentContent() {
		this.setMinWidth(DEFAULT_MIN_WIDTH);
		this.setMinHeight(DEFAULT_MIN_HEIGHT);
	}
	
	public boolean isResizableContent() {
		return true;
	}
	
	public ComponentView getView() {
		return (ComponentView) this.getParent();
	}
	
	public Space getSpace() {
		return (Space) getView().getParent();
	}
	
	public abstract MenuItem[] getMenuItems();
	
	public abstract void update(UpdateType updateType, Object... data);
}
