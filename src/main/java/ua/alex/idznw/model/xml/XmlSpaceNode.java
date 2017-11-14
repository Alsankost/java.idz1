package ua.alex.idznw.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ua.alex.idznw.view.ComponentView;
import ua.alex.idznw.view.ConnectionView;
import ua.alex.idznw.view.Space;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "space")
public class XmlSpaceNode {
	
	@XmlElement
	@XmlElementWrapper(name = "components")
	private XmlComponentNode[] component;
	
	@XmlElement
	@XmlElementWrapper(name = "connections")
	private XmlConnectionNode[] connection;
	
	public XmlSpaceNode() {
		this.component  = null;
		this.connection = null;
	}
	
	public XmlSpaceNode(int componentsCount, int connectionCount) {
		this.component  = new XmlComponentNode[componentsCount];
		this.connection = new XmlConnectionNode[connectionCount];
	}
	
	public void setComponent(int i, XmlComponentNode cmn) {
		this.component[i] = cmn;
	}
	
	public void setConnection(int i, XmlConnectionNode cnn) {
		this.connection[i] = cnn;
	}
	
	public XmlComponentNode getComponent(int i) {
		return component[i];
	}
	
	public XmlConnectionNode getConnection(int i) {
		return connection[i];
	}
	
	public int getComponentsCount() {
		return component.length;
	}
	
	public int getConnectionsCount() {
		return connection.length;
	}
	
	public Space createSpace() {
		Space space = new Space();
		
		ComponentView[] components = new ComponentView[this.component.length];
		//System.out.println(this.component.length);
		for (int i = 0; i < this.component.length; i++) {
			//System.out.println("AUX " + component[i]);
			components[i] = component[i].createComponent();
			if (components[i] == null) continue;
		space.getChildren().add(components[i]);
		}
		//space.getChildren().addAll(components);
		
		System.out.println(this.connection.length);
		for (int i = 0; i < this.connection.length; i++) {
			XmlConnectionNode cn = connection[i];
			ConnectionView tmp = new ConnectionView(components[cn.getFirstId()], components[cn.getSecondId()]);
			space.getChildren().add(tmp);
			tmp.toBack();
		}
		
		return space;
	}
}
