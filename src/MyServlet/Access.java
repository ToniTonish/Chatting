package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Access
 */
@WebServlet("/Access")
public class Access extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Access() {
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
		
		DBConnectionManager dbcon = new DBConnectionManager();
		
		
		switch (Integer.parseInt(request.getParameter("method"))) {
		case 1:
			//Registration			
			registration(request, response, dbcon);
			break;
		case 2:
			//Login
			login(request, response, dbcon);
			break;
		case 3:
			//Logout
			logout(request, response);
			break;
		case 4:
			//Delete
			delete(request, response, dbcon);
			break;
		}
	}

	
	
	protected void registration (HttpServletRequest request, HttpServletResponse response, DBConnectionManager dbcon) {
		UserBean user = new UserBean();
		HttpSession session = request.getSession(true);
		boolean success = false;
		
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setMail(request.getParameter("email"));
		user.setSex(request.getParameter("sex"));
		
		session.setAttribute("keepLogIn", false);

		try {
			success = dbcon.registration(user);
			
			if (success) {
    			session.setAttribute("keepLoggedIn", false);
    			session.setAttribute("username", user.getUsername());
    			int id = dbcon.idReturn(user.getUsername());
    			session.setAttribute("userid", id);
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
			session.setMaxInactiveInterval(-1);
    	} catch (Throwable theException) {
    		theException.printStackTrace(); 
    	}
	}
	
	protected void login (HttpServletRequest request, HttpServletResponse response, DBConnectionManager dbcon) {
		UserBean user = new UserBean();
		HttpSession session = request.getSession(true);
		boolean success = false;
		ResultSet rs = null;
		boolean kl;
		
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		
		kl = Boolean.parseBoolean(request.getParameter("kl"));
		
		session.setAttribute("keepLogIn", kl);
		if (kl) {
			Cookie c = new Cookie("JSESSIONID", session.getId());  
            // store for a day  
            c.setMaxAge(60*60*24);
            response.addCookie(c);  
		}
		
		try {
			rs = dbcon.login(user);
			success = rs.next();
			if (success) {
				session.setAttribute("username", user.getUsername());
				session.setAttribute("userid", rs.getInt("user_id"));
				// prepare response
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append("true");
				out.close();
			} else {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
	    		// prepare response
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.append("false");
				out.close();
			}
			session.setAttribute("Logged", success);
			session.setMaxInactiveInterval(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void logout (HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		session.setAttribute("Logged", false);
		session.invalidate();
		try {
			// prepare response
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.append("true");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void delete (HttpServletRequest request, HttpServletResponse response, DBConnectionManager dbcon) {
		HttpSession session = request.getSession(false);
		int id = (int) session.getAttribute("userid");
		try {
			String sql = "DELETE FROM users WHERE user_id = " + id;
			int res = dbcon.insert(sql);
			
			if (res > 0) {
				System.out.println("Delete Account Succesful");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
