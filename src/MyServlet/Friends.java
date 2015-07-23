package MyServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddFriends
 */
@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Connection currentCon = null;
	static ResultSet rs = null;  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
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

		HttpSession session = request.getSession(false);

		int userId2;
		int idUserLogged;

		userId2 = Integer.parseInt(request.getParameter("idUser2")); //aggiunto questo per averlo intero
		idUserLogged = (int) session.getAttribute("userid");
		System.out.println(Integer.toString(userId2));
		System.out.println(Integer.toString(idUserLogged));
		
		DBConnectionManager dbcon = new DBConnectionManager();
		String sqlquery = null;
		String result = null;
		
		if (userId2 != idUserLogged) {
			switch (Integer.parseInt(request.getParameter("method"))) {
			case 1:
				// ADD FRIEND
					sqlquery = "INSERT INTO address_book (u_one, u_two) VALUES "
							+ "('" + idUserLogged + "',"
							+ "'" + userId2 + "')";	
					result = "You added your friend successfully...";
					
				break;
			case -1:
				// REMOVE FRIEND
				sqlquery = "DELETE FROM address_book WHERE u_one = " + idUserLogged + " AND u_two = " + userId2;
				result = "You removed your ex-friend successfully...";
				break;
			}
			
			try {
				if(dbcon.insert(sqlquery) > 0) {
					System.out.print(result);
				}
			} catch (Exception ex) {
				System.out.println("Add failed: An Exception has occurred! " + ex);
			}
			 
		}
	}
}
