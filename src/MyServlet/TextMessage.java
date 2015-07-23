package MyServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TextMessage
 */
@WebServlet("/TextMessage")
public class TextMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TextMessage() {
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
		HttpSession session = request.getSession(true);
		DBConnectionManager dbcm = new DBConnectionManager();

		int u_one, u_two;

		String text1 = request.getParameter("textMessage");


//		u_one = dbcm.idReturn(currentUser);
		u_one = (int) session.getAttribute("userid");
//		u_two = dbcm.idReturn(u_receiver);
		u_two = Integer.parseInt(request.getParameter("userIdR"));
		
		int first, second;
		if (u_one < u_two) {
			first = u_one;
			second = u_two;
		} else {
			first = u_two;
			second = u_one;
		}

		try {
			ResultSet rs = dbcm.checkConv(first, second);
			if(rs.next()) {
				int id_conv = rs.getInt(1);
				//String sqlQuery = "INSERT INTO conversation_reply VALUES (null,'" + text1 + "', "+u_one+",now(),"+id_conv+")";
				dbcm.insertText(text1, u_one, id_conv);
				String sqlQuery = "UPDATE conversation SET time=now(), new_message = TRUE WHERE c_id="+id_conv;
				dbcm.insert(sqlQuery);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
