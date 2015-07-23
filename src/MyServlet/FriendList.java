package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Servlet implementation class FriendList
 */
@WebServlet("/FriendList")
public class FriendList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Connection currentCon = null;
	static ResultSet rs = null;  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
    	int index = (int) session.getAttribute("userid"); //= idReturn(username, request);
				    
	   try {

		   DBConnectionManager dbcon = new DBConnectionManager();
		   rs = dbcon.search("select user_id, username from users JOIN address_book ON user_id = u_two where u_one LIKE '" + index + "'");
 	      
	      Document doc = creaXML();
	      // qui si riempe
	      aggiungiAmici(rs, doc);
	      
	      outputXML(response.getWriter(), doc);
	      
	   } catch (Exception ex) {
	      System.out.println("Search failed: An Exception has occurred! " + ex);
	   } 
	} 

	private Document creaXML() {
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("users");
		rootElement.setAttribute("xmlns", "localhost/Chatting/friend_list");
		doc.appendChild(rootElement);
		
		return doc;
		
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return null;
		}
	}
	
	private void outputXML(PrintWriter pw, Document doc) {
		try {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		// riga per cambiare encoding e avere le lettere accentate
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "iso-8859-1");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(pw);
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void aggiungiAmici(ResultSet rs, Document doc) {
		try {
			while (rs.next()) {
				Element user = doc.createElement("user");
				Element username = doc.createElement("username");
				Element userId = doc.createElement("userId");
				
				userId.appendChild(doc.createTextNode(rs.getInt("user_id")+""));
				username.appendChild(doc.createTextNode(rs.getString("username")));
				
				user.appendChild(userId);
				user.appendChild(username);
				
				doc.getElementsByTagName("users").item(0).appendChild(user);
				System.out.println(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
