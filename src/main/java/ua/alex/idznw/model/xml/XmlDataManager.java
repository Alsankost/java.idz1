package ua.alex.idznw.model.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.scene.Node;
import ua.alex.idznw.view.ComponentView;
import ua.alex.idznw.view.ConnectionView;
import ua.alex.idznw.view.Space;

public abstract class XmlDataManager {
	public static void save(Space space, File file) throws JAXBException {
		List<ComponentView>  components  = new ArrayList<ComponentView>();
		List<ConnectionView> connections = new ArrayList<ConnectionView>();
		
		for (Node item : space.getChildren()) {
			if (item instanceof ComponentView) {
				components.add((ComponentView) item);
				continue;
			}
			
			if (item instanceof ConnectionView) {
				connections.add((ConnectionView) item);
				continue;
			}
		}
		
		XmlSpaceNode root = new XmlSpaceNode(components.size(), connections.size());
		for (int i = 0; i < components.size(); i++) {
			root.setComponent(i, new XmlComponentNode(i, components.get(i)));
		}
		
		for (int i = 0; i < connections.size(); i++) {
			ConnectionView connection = connections.get(i);
			root.setConnection(i, new XmlConnectionNode(components.indexOf(connection.getFirstPoint()),
														components.indexOf(connection.getSecondPoint())));
		}
		
		JAXBContext jc = JAXBContext.newInstance(XmlDataManager.class.getPackage().getName());
		Marshaller m = jc.createMarshaller();
		m.marshal(root, file);
		return;
	}
	
	public static Space load(File file) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(XmlDataManager.class.getPackage().getName());
		Unmarshaller m = jc.createUnmarshaller();
		XmlSpaceNode sn = (XmlSpaceNode) m.unmarshal(file);
		return sn.createSpace();
	}
}
