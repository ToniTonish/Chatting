package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class OpenConversation
 */
@WebServlet("/OpenConversation")
public class OpenConversation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenConversation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		DBConnectionManager dbcm = new DBConnectionManager();

		int u_one, u_two;
//		String currentUser = (String) session.getAttribute("username");
		

		u_one = (int) session.getAttribute("userid");
		u_two = Integer.parseInt(request.getParameter("userIdR"));

		try {
			int first, second;
			// if conversation exist, don't create a new row.
			if (u_one < u_two) {
				first = u_one;
				second = u_two;
			} else {
				first = u_two;
				second = u_one;
			}
			
			int c_id = -1;
			ResultSet rs = dbcm.checkConv(first, second);
			boolean exist_conv = rs.next();
			
			if (!exist_conv) {
				String sqlQuery = "INSERT INTO conversation VALUES (null," + first + "," + second + ",now(), false)";
				dbcm.insert(sqlQuery);
			} else {
//				//Take the last chat to check if there are new message for you
//				String sqlcheckNM = "SELECT * FROM conversation_reply WHERE c_id_fk = "+c_id+" ORDER BY time DESC LIMIT 1";
//				rs = dbcm.search(sqlcheckNM);
//				rs.next();
				c_id = rs.getInt("c_id");
				
				String sqlConv = "SELECT username, reply, conversation_reply.time FROM users JOIN conversation_reply ON user_id = user_id_fk WHERE c_id_fk="+c_id+" ORDER BY time ASC";
				rs = dbcm.search(sqlConv);

				Document doc = createXML();
				addNode(rs, doc);
				outputXML(response.getWriter(), doc);
				
				if (dbcm.checkNewMessage(c_id) != u_one) {
					sqlConv = "UPDATE conversation SET new_message = FALSE WHERE c_id="+c_id;
					dbcm.insert(sqlConv);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error OpenConversation");
			e.printStackTrace();
		}

	}

	private void outputXML(PrintWriter pw, Document doc) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// Change encoding for the italian letters
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "iso-8859-1");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(pw);

			// Output of XML to console for testing
//			StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Document createXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("conversation");
			rootElement.setAttribute("xmlns", "localhost/Chatting/conversation");
			doc.appendChild(rootElement);

			return doc;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return null;
		}
	}

	private void addNode(ResultSet rs, Document doc) {
		try {
			while (rs.next()) {
				// Take messages
				String username = rs.getString(1);
				String text_p = rs.getString(2);
				Timestamp timestamp = rs.getTimestamp(3);
				// Create Elements
				Element message = doc.createElement("message");
				Element user = doc.createElement("user");
				Element text = doc.createElement("text");
				Element time = doc.createElement("time");
				// Insert nodes in the xml
				user.appendChild(doc.createTextNode(username));
				text.appendChild(doc.createTextNode(text_p));
				SimpleDateFormat dt = new SimpleDateFormat("dd MMM, HH:mm");
				time.appendChild(doc.createTextNode(dt.format(timestamp)));

				message.appendChild(user);
				message.appendChild(text);
				message.appendChild(time);

				doc.getFirstChild().appendChild(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
