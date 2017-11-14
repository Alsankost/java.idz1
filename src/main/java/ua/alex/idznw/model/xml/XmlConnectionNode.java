package ua.alex.idznw.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "connection")
public class XmlConnectionNode {
	@XmlAttribute
	private int firstComponentId, secondComponentId;
	
	public XmlConnectionNode() {
		this.firstComponentId  = 0;
		this.secondComponentId = 0;
	}
	
	public XmlConnectionNode(int firstId, int secondId) {
		this.firstComponentId  = firstId;
		this.secondComponentId = secondId;
	}
	
	public int getFirstId() {
		return this.firstComponentId;
	}
	
	public int getSecondId() {
		return this.secondComponentId;
	}
}
