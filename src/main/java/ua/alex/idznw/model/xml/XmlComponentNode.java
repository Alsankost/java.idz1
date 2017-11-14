package ua.alex.idznw.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ua.alex.idznw.view.ComponentContent;
import ua.alex.idznw.view.ComponentView;
import ua.alex.idznw.view.model.ComponentsSet;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "component")
public class XmlComponentNode {
	@XmlAttribute
	private String type;
	
	@XmlAttribute
	private double x, y;
	
	@XmlAttribute
	private long id;
	
	@XmlElement
	private String[] data;
	
	public XmlComponentNode() {
		this.type = null;
		this.x = 0;
		this.y = 0;
		this.id = 0;
		this.data = null;
	}
	
	public XmlComponentNode(String type, double x, double y, long id, String... data) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.id = id;
		this.data = data;
	}
	
	public XmlComponentNode(long id, ComponentView component) {
		this.id = id;
		this.x = component.getLayoutX();
		this.y = component.getLayoutY();
		this.type = component.getContent().getClass().getSimpleName();
		this.data = component.getContent().getData();
	}
	
	public ComponentView createComponent() {
		ComponentContent content = ComponentsSet.createContent(type.substring(0, type.length() - 9));
		//System.out.println(type.substring(0, type.length() - 9) + " - " + (content != null));
		if (content == null) return null;
		content.setData(data);
		return new ComponentView(y, x, content);
	}
	
	public long getId() {
		return id;
	}
}
