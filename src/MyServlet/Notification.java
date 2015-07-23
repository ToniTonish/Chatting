package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Notification
 */
@WebServlet("/Notification")
public class Notification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notification() {
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
		HttpSession session = request.getSession(true);
		DBConnectionManager dbcm = new DBConnectionManager();

		int u_one, idLastM;

		u_one = (int) session.getAttribute("userid");
		//idLastM = Integer.parseInt(request.getParameter("userIdR"));

		String sql = "SELECT * FROM conversation WHERE user_one = " + u_one + " OR user_two = "+ u_one;
		
		ResultSet rs;
		rs = dbcm.search(sql);
		PrintWriter out = null;
		response.setContentType("text/html");
		out = response.getWriter();
		boolean tmp = false;
		try {
			while(rs.next()) {
				ResultSet rs2 = dbcm.checkConv(rs.getInt("user_one"), rs.getInt("user_two"));
				rs2.next();
				boolean nm = rs2.getBoolean("new_message");
				if (nm) {
					idLastM = dbcm.checkNewMessage(rs2.getInt("c_id"));
					//idLastM = rs2.getInt("user_two");
					out.append(idLastM+";");
					tmp = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!tmp) {
			out.append("false");
		}
		out.close();
		}
}
