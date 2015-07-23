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

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
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
		UserBean user = new UserBean();
		HttpSession session = request.getSession(true);
		ResultSet rs = null;
		String username = (String) session.getAttribute("username");
		
		user.setUsername(username);
		user.setPassword(request.getParameter("oldPassword"));
		String newPassword = request.getParameter("newPassword");
		DBConnectionManager dbcon = new DBConnectionManager();
		try {
			rs = dbcon.login(user);
			if (rs.next()) {
				String sql = "UPDATE users SET password=MD5('" + newPassword + "') WHERE username='" + username + "';";
				if (dbcon.insert(sql) > 0) {
					// prepare response
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.append("true");
					out.close();
				} else {
		    		// prepare response
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.append("false");
					out.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
