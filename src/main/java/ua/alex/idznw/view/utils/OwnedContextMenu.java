package ua.alex.idznw.view.utils;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;

public class OwnedContextMenu extends ContextMenu {
	private Node owner = null;
	
	public void setOwner(Node owner) {
		this.owner = owner;
	}
	
	public Node getOwner() {
		return owner;
	}
}
