package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static ResultSet rs = null;  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		String username = request.getParameter("un");
		HttpSession session = request.getSession();
		int user_id = (int) session.getAttribute("userid");
		try {
			// Search all user matches
			DBConnectionManager dbcon = new DBConnectionManager();
			rs = dbcon.searchUser(username, user_id);
			Document doc = createXML();
			// populate XML file
			addFriendsToXML(rs, doc);
			// send XML
			outputXML(response.getWriter(), doc);
		} catch (Exception ex) {
			System.out.println("Search failed: An Exception has occurred! "
					+ ex);
		}
    }
	
	private Document createXML() {
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("users");
		rootElement.setAttribute("xmlns", "localhost/Chatting/search_friends");
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
			
		}
	}
	
	private void addFriendsToXML(ResultSet rs, Document doc) {
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
