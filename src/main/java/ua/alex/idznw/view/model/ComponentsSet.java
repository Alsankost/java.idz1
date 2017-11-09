package ua.alex.idznw.view.model;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ua.alex.idznw.Start;
import ua.alex.idznw.view.content.NetworkComponent;

public abstract class ComponentsSet {
	
	public static final double WIDTH  = 60,
							   HEIGHT = 60,
							   W_PADDING = 10,
							   H_PADDING = 10;
	
	public static void addComponent(ComponentContent content, String name) {
		BorderPane container = new BorderPane();
		container.setMaxWidth(WIDTH + W_PADDING * 2);
		container.setMaxHeight(HEIGHT + H_PADDING * 2);
		
		content.setPrefWidth(WIDTH);
		content.setPrefHeight(HEIGHT);
		container.setCenter(content);
		
		Text text = new Text(name);
		text.setWrappingWidth(WIDTH);
		text.setTextAlignment(TextAlignment.CENTER);
		container.setBottom(text);
		
		Start.getMainContoller().addToComponentSet(container);
	}
	
	public static void init() {
		addComponent(new NetworkComponent(), "Network");
	}
}
