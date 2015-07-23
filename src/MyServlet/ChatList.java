package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
 * Servlet implementation class ChatList
 */
@WebServlet("/ChatList")
public class ChatList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Connection currentCon = null;
	static ResultSet rs = null;  

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatList() {
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
		int index = (int) session.getAttribute("userid");
		
		try {
			DBConnectionManager dbcon = new DBConnectionManager();
			rs = dbcon.search("SELECT c.*, u1.username as user_one_un, u2.username as user_two_un FROM conversation as c JOIN users as u1 ON user_one = u1.user_id JOIN users as u2 ON u2.user_id = user_two WHERE user_one ="+index+" OR user_two ="+index+" ORDER BY time DESC");
			
			Document doc = creaXML();
			// qui si riempe
			addInChat(rs, doc, index);

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
			Element rootElement = doc.createElement("chatlist");
			rootElement.setAttribute("xmlns", "localhost/Chatting/chat_list");
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

	private void addInChat(ResultSet rs, Document doc, int index) {
		try {
			String usernm;
			int id_receiver;
			while (rs.next()) {
				if (index != rs.getInt("user_one")) {
					usernm = rs.getString("user_one_un");
					id_receiver = rs.getInt("user_one");
				} else {
					usernm = rs.getString("user_two_un");
					id_receiver = rs.getInt("user_two");
				}
				
				Timestamp timestamp = rs.getTimestamp("time");
				
				Element chat = doc.createElement("chat");
				Element userId = doc.createElement("userId");
				Element username = doc.createElement("username");
				Element time = doc.createElement("time");

				userId.appendChild(doc.createTextNode(id_receiver+""));
				username.appendChild(doc.createTextNode(usernm));
				SimpleDateFormat dt = new SimpleDateFormat("dd MMM, HH:mm");
				time.appendChild(doc.createTextNode(dt.format(timestamp)));

				chat.appendChild(userId);
				chat.appendChild(username);
				chat.appendChild(time);

				doc.getFirstChild().appendChild(chat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


