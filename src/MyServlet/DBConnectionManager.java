package MyServlet;

import java.sql.*;

public class DBConnectionManager {
	// COSTANTS
	private final static String url = "jdbc:mysql://localhost:3306/chat_DB";
	private final static String usernameDB = "root";
	//private final static String passwordDB = "apsw1112";
	private final static String passwordDB = "";
	private static Connection con = null;

	private static String U_REGISTRATION = "INSERT INTO users VALUES(null,?,MD5(?),?,?)";
	private static String Q_SEARCH_USER_LOGIN = "SELECT * FROM users WHERE username = ? AND password = MD5(?)";
	private static String Q_SEARCH_USER = "SELECT * FROM users WHERE username LIKE ? AND user_id != ?";
	private static String Q_GET_USER_ID = "SELECT user_id FROM users WHERE username = ?";
	private static String Q_CHECK_C_ID = "SELECT * FROM conversation WHERE (user_one = ?) AND (user_two = ?)";
//	private static String Q_LOAD_CONVERSATION = "SELECT username1, user_one, username AS username2, user_two FROM users INNER JOIN (SELECT username AS username1, user_one, user_two FROM users INNER JOIN conversation ON users.user_id = conversation.user_one) AS mytable ON users.user_id = mytable.user_two";
	private static String Q_CHECK_NEWMESSAGE = "SELECT user_id_fk FROM conversation JOIN conversation_reply ON c_id = c_id_fk WHERE c_id_fk = ? AND new_message = 1 ORDER BY conversation_reply.time DESC limit 1";
//	private static String Q_ADDR_BOOK = "SELECT u_two FROM ";
	private static String Q_INS_TEXT = "INSERT INTO conversation_reply VALUES (null,?, ?,now(),?)";
	
	// ATTRIBUTES
	private ResultSet rs;
	private PreparedStatement ps;
	
	public DBConnectionManager() {
		// initialize attributes
		rs = null;
		ps = null;
		
		if (con == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {            	
					con = DriverManager.getConnection(url,usernameDB,passwordDB);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet getRS() {
		return rs;
	}
	
	public boolean insertText(String text, int u_one,int id_conv) {
		try {
			ps = con.prepareStatement(Q_INS_TEXT);

			ps.setString(1, text);
			ps.setInt(2, u_one);
			//ps.setString(3, "now()");
			ps.setInt(3, id_conv);
			if(ps.executeUpdate() > 0) {
				System.out.println("text was successfully insert...");
				return true;
			}
		}  catch (Exception ex) {
			System.out.println("Text failed: An Exception has occurred! " + ex);
		}
		return false;
	}

	public Boolean registration(UserBean bean) {
		String username = bean.getUsername();    
		String password = bean.getPassword(); 
		String sex = bean.getSex();
		String email = bean.getMail();
		try {
			ps = con.prepareStatement(U_REGISTRATION);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, sex);
			bean.setValid(true);
			
			if(ps.executeUpdate() > 0) {
				System.out.print("You are successfully registered...");
				return true;
			}
		} catch (Exception ex) {
			System.out.println("Registration failed: An Exception has occurred! " + ex);
			return false;
		}
		return false;
	}
	
	public ResultSet login(UserBean bean) {
	      //preparing some objects for connection 
	      ResultSet rs = null;
	      String username = bean.getUsername();
	      String password = bean.getPassword();
		    
	   try {
		   PreparedStatement ps = con.prepareStatement(Q_SEARCH_USER_LOGIN);
		   ps.setString(1, username);
		   ps.setString(2, password);
		   rs = ps.executeQuery();
	   } catch (Exception ex) {
		   System.out.println("Log In failed: An Exception has occurred! " + ex);
	   }
	   return rs;
	}
	
	public ResultSet searchUser(String un, int except_id) {
		ResultSet rs = null;
		
		try {
			   PreparedStatement ps = con.prepareStatement(Q_SEARCH_USER);
			   ps.setString(1, un + "%");
			   ps.setInt(2, except_id);
			   rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet search(String query) {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;		
	}
	
	public int insert(String query) {
		Statement stmt = null;
		int res = 0;
		try {
			stmt = con.createStatement();
			res = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public Boolean addressbookINS(UserBean bean) {
		//preparing some objects for connection 
		String username = bean.getUsername();    
		String password = bean.getPassword(); 
		String sex = bean.getSex();
		String email = bean.getMail();

		try {
			ps = con.prepareStatement(U_REGISTRATION);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, sex);
			bean.setValid(true);
			
			if(ps.executeUpdate() > 0) {
				System.out.print("You are successfully registered...");
				return true;
			}
		} catch (Exception ex) {
			System.out.println("Registration failed: An Exception has occurred! " + ex);
			return false;
		}
		return false;
	}
	
	public int idReturn(String usrnm){
		
		PreparedStatement ps = null;
	        
	   int indexUserLogged = 0;
	        try {
				ps = con.prepareStatement(Q_GET_USER_ID);
				ps.setString(1, usrnm);
		        rs = ps.executeQuery();	        
		        if(rs.next())
		        	indexUserLogged = rs.getInt(1);
		        else
		        	indexUserLogged = -1;
		       
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return indexUserLogged;
	}
	
	public ResultSet checkConv (int u1, int u2) {
		PreparedStatement ps = null;
//		rs = null;
		try {
			ps = con.prepareStatement(Q_CHECK_C_ID);
			ps.setInt(1, u1);
			ps.setInt(2, u2);
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = null;
			System.out.println("rs null");
		}
		return rs;
	}
	
	public int checkNewMessage(int conv) {
		PreparedStatement ps = null;
		int id;
		try {
			ps = con.prepareStatement(Q_CHECK_NEWMESSAGE);
			ps.setInt(1, conv);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			} else {
				id = -1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = null;
			id = -1;
			System.out.println("rs null");
		}
		return id;
	}
}