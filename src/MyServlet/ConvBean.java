package MyServlet;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvBean {
	Document doc;
	String rootElement;
	
	ConvBean() {
		
	}
	
	public String getRootElement() {
		return rootElement;
	}
	
	public void setRootElement(String root_e) {
		rootElement = root_e;
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setDoc(Document d) {
		doc = d;
	}
	
	public void createXML(String xmlns) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
 
			// root elements
//			Document document = docBuilder.newDocument();
			Element rootElem = doc.createElement(rootElement);
			rootElem.setAttribute("xmlns", xmlns);
			doc.appendChild(rootElem);

//			setDoc(document);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addNode(String username, String text_p, Date time_p) {
		try {
			Document doc1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Document doc = this.getDoc();
			Element message = doc1.createElement("message");
			Element user = doc1.createElement("user");
			Element text = doc1.createElement("text");
			Element time = doc1.createElement("time");

			SimpleDateFormat dt = new SimpleDateFormat("dd MMM, HH:mm");

			user.appendChild(doc1.createTextNode(username));
			text.appendChild(doc1.createTextNode(text_p));
			time.appendChild(doc1.createTextNode(dt.format(time_p)));

			message.appendChild(user);
			message.appendChild(text);
			message.appendChild(time);
			doc1.getFirstChild().appendChild(message);
			//				doc.getElementsByTagName(rootElement).item(0).appendChild(message);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("si ferma qua bla bla bla");
		}
	}

}
