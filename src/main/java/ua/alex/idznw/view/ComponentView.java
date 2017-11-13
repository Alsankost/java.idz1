package ua.alex.idznw.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ua.alex.idznw.Start;
import ua.alex.idznw.view.model.SelectionModel;
import ua.alex.idznw.view.model.Tool;
import ua.alex.idznw.view.model.UpdateType;
import ua.alex.idznw.view.utils.OwnedContextMenu;

public class ComponentView extends Pane {
	//==Constants:
	public static final double DEFAULT_WIDTH  = 100,
							   DEFAULT_HEIGHT = 100,
							   MIN_WIDTH      = 60,
							   MIN_HEIGHT     = 60,
							   
							   RESIZE_BLOCK_WIDTH  = 8,
							   RESIZE_BLOCK_HEIGHT = 8;
							   
	public static final String SELECTION_STYPE = "selection_component"; 
	
	//==Selection property
	private BooleanProperty selectionProperty = new SimpleBooleanProperty();
	
	//==Coordinates for start point from drag
	private double startMoveX = 0, startMoveY = 0;
	
	//==Context menu for component
	private OwnedContextMenu contextMenu = new OwnedContextMenu();
	/*
	public void addItemToContextMenu(MenuItem item) {
		contextItem.getItems().add(item);
	}*/
	
	//==Content:
	private ComponentContent content = null;
	
	//==Action listeners:
	//Resize event
	private static final EventHandler<MouseEvent> resizeBlockDragListener = (e) -> {
		ComponentView block = (ComponentView) ((Rectangle) e.getSource()).getParent();
		
		SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
		selectionModel.selectOne(block);
		
		double dx = e.getX();
		double dy = e.getY();
		
		block.setSize(dx, dy);
	};
	
	//Mouse events
	private static final EventHandler<MouseEvent> thisEnteredListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null) {
			( (Space) block.getParent() ).setComponentFocus(true);
		}
	};
	
	private static final EventHandler<MouseEvent> thisExitedListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null) {
			( (Space) block.getParent() ).setComponentFocus(false);
		}
	};
	
	private static final EventHandler<MouseEvent> thisClickListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (block != null &&
			Math.abs(block.startMoveX - e.getX()) < 1 &&
			Math.abs(block.startMoveY - e.getY()) < 1) {
			SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
			selectionModel.selectOne(block);
		}
	};
	
	private static final EventHandler<MouseEvent> thisPressListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		block.startMoveX = e.getX();
		block.startMoveY = e.getY();
	};
	
	private static final EventHandler<MouseEvent> thisDragListener = (e) -> {
		ComponentView block = (ComponentView) e.getSource();
		if (e.getX() > ComponentView.RESIZE_BLOCK_WIDTH &&
			e.getX() < block.getPrefWidth() - ComponentView.RESIZE_BLOCK_WIDTH &&
			e.getY() > ComponentView.RESIZE_BLOCK_HEIGHT &&
			e.getY() < block.getPrefHeight() - ComponentView.RESIZE_BLOCK_HEIGHT) {
			SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
			if (block.isSelected()) {
				selectionModel.moveComponents(block.startMoveX, block.startMoveY, e.getX(), e.getY());
				return;
			}
			else {
				selectionModel.selectOne(block);
				return;
			}
		}
		
		if (block.isSelected()) {
			SelectionModel selectionModel = ( (Space) block.getParent() ).getSelectionModel();
			selectionModel.moveComponents(block.startMoveX, block.startMoveY, e.getX(), e.getY());
		}
		e.consume();
	};
	
	//Drag and drop:
	private static final EventHandler<MouseEvent> elementDrag = (e) -> {
		if (Start.getMainContoller().getCurrentTool() != Tool.CONNECT &&
			Start.getMainContoller().getCurrentTool() != Tool.DISCONNECT) return;
		
		ComponentView item = (ComponentView) e.getSource();
        Dragboard db = item.startDragAndDrop(TransferMode.LINK);
        
        ClipboardContent content = new ClipboardContent();
        
        content.putString((Start.getMainContoller().getCurrentTool() == Tool.CONNECT)?"..":"-.");
        db.setContent(content);

        e.consume();
	};
	
	private static final EventHandler<DragEvent> dragOver = (e) -> {
		e.acceptTransferModes(TransferMode.LINK);
	};	
	
	private static final EventHandler<DragEvent> dragExited = (e) -> {
		e.acceptTransferModes(TransferMode.LINK);
        e.consume();
	};	
	
	private static final EventHandler<DragEvent> dragDrop = (e) -> {
		ComponentView second = (ComponentView) e.getSource();
		//added = bp.getCenter().getClass();
		Dragboard db = ((DragEvent) e).getDragboard();
		String name = db.getString();
		
		Space space = ((Space) second.getParent());
		
		System.out.println("--kkk-- " + name);
		
		switch (name) {
			case "..":
				ComponentView first = space.getSelectionModel().getOne();
				if (first != null && !first.equals(second)) {
					ConnectionView connection = new ConnectionView(first, second);
					space.getChildren().add(connection);
					connection.toBack();
				}
			break;
			
			case "-.":
			break;
		}	
		//System.out.println(((ComponentView) e.getSource()).getContent());
	};
	
	//Listeners for context menu items
	private EventHandler<ActionEvent> deleteAction = (e) -> {
		suicide();
	};
	
	//==Constructor:
	public ComponentView(double x, double y, ComponentContent content) {		
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefWidth(content.getPrefWidth());
		this.setPrefHeight(content.getPrefHeight());
		
		this.content = content;
		
		if (content.isResizableContent()) {
			Rectangle resizeBlock = new Rectangle();
			resizeBlock.xProperty().bind(this.widthProperty().subtract(RESIZE_BLOCK_WIDTH));
			resizeBlock.yProperty().bind(this.heightProperty().subtract(RESIZE_BLOCK_HEIGHT));
			resizeBlock.setWidth(RESIZE_BLOCK_WIDTH);
			resizeBlock.setHeight(RESIZE_BLOCK_HEIGHT);
			
			resizeBlock.setOnMouseDragged(resizeBlockDragListener);
			
			this.getChildren().add(resizeBlock);
		}
		
		content.setLayoutX(RESIZE_BLOCK_WIDTH);
		content.setLayoutY(RESIZE_BLOCK_HEIGHT);
		content.prefWidthProperty().bind(prefWidthProperty().subtract(RESIZE_BLOCK_WIDTH * 2));
		content.prefHeightProperty().bind(prefHeightProperty().subtract(RESIZE_BLOCK_HEIGHT * 2));
		this.getChildren().add(content);
		
		this.minWidthProperty().bind(content.minWidthProperty().add(RESIZE_BLOCK_WIDTH * 2));
		this.minHeightProperty().bind(content.minHeightProperty().add(RESIZE_BLOCK_HEIGHT * 2));
		
		//contextMenu.getItems().add(new MenuItem("this is menu item!"));
		MenuItem deleteItem = new MenuItem("Delete");
		deleteItem.setOnAction(deleteAction);
		contextMenu.getItems().add(deleteItem);
		this.setOnContextMenuRequested((e) -> {
			contextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY());
		});
		MenuItem[] contentMenuIems = content.getMenuItems();
		if (contentMenuIems != null) {
			contextMenu.getItems().add(new SeparatorMenuItem());
			contextMenu.getItems().addAll(contentMenuIems);
		}
		
		this.setOnMouseEntered(thisEnteredListener);
		this.setOnMouseExited(thisExitedListener);
		this.setOnMouseClicked(thisClickListener);
		this.setOnMousePressed(thisPressListener);
		this.setOnMouseDragged(thisDragListener);
		
		this.setOnDragOver(dragOver);
		this.setOnDragEntered(dragExited);
		this.setOnDragDropped(dragDrop);
		this.setOnDragDetected(elementDrag);
		//this.setOnDragDetected(elementDrag);
		//this.setOnMouseDragReleased(dragDrop);
	}
	
	public ComponentContent getContent() {
		return content;
	}
	
	public void setPosition(double x, double y) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		content.update(UpdateType.MOVE, x, y);
	}
	
	public void setSize(double w, double h) {
		this.setPrefWidth(w);
		this.setPrefHeight(h);
		content.update(UpdateType.RESIZE, w, h);
	}
	
	public void selectionOn() {
		this.selectionProperty.set(true);
		this.getStyleClass().add(SELECTION_STYPE);
		this.applyCss();
		content.update(UpdateType.SELECT, true);
	}
	
	public void selectionOff() {
		this.selectionProperty.set(true);
		this.getStyleClass().remove(SELECTION_STYPE);
		this.applyCss();
		content.update(UpdateType.SELECT, false);
	}
	
	public boolean isSelected() {
		return ( (Space) this.getParent() ).getSelectionModel().isSelected(this);
	}
	
	public BooleanProperty getSelectionProperty() {
		return this.selectionProperty;
	}
	
	public void suicide() {
		content.update(UpdateType.KILL);
		((Space) this.getParent()).getChildren().remove(this);
	}
}
