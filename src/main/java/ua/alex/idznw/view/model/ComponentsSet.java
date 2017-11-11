package ua.alex.idznw.view.model;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ua.alex.idznw.Start;
import ua.alex.idznw.view.content.NetworkComponent;
import ua.alex.idznw.view.content.TableComponent;

public abstract class ComponentsSet {
	
	public static final double WIDTH  = 60,
							   HEIGHT = 60,
							   W_PADDING = 10,
							   H_PADDING = 10;
	
	public static Map<String, Class<?>> collection = new HashMap<String, Class<?>>();
	
	static {
		collection.put("Network", NetworkComponent.class);
		collection.put("Table", TableComponent.class);
	};
	
	//private static Class<?> added = null;
	
	public static ComponentContent createContent(String name) {
		ComponentContent result = null;
		try {
			result = (ComponentContent) collection.get(name).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static final EventHandler<MouseEvent> elementDrag = (e) -> {
		System.out.println("onDragDetected");

		BorderPane item = (BorderPane) e.getSource();
        Dragboard db = item.startDragAndDrop(TransferMode.ANY);
        /*
    	DropShadow dropShadow = new DropShadow();
    	dropShadow.setRadius(5.0);
    	dropShadow.setOffsetX(3.0);
    	dropShadow.setOffsetY(3.0);
    	dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
    	item.setEffect(dropShadow);
        /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();
        
        content.putString( ((Text) item.getBottom()).getText());
        db.setContent(content);

        e.consume();
	};
	
	private static void addComponent(ComponentContent content, String name) {
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
		
		container.setOnDragDetected(elementDrag);
		//container.setOnMouseReleased(elementDrop);
		
		Start.getMainContoller().addToComponentSet(container);
	}
	
	public static void init() {
		System.out.println(collection.keySet().size());
		for (String key : collection.keySet()) {
			System.out.println(key);
			try {
				addComponent((ComponentContent) collection.get(key).newInstance(), key);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
