package MyServlet;

public class UserBean {
	
    private String username;
    private String password;
//    private String firstName;
//    private String lastName;
    private String eMail;
//    private String year;
//    private String month;
//    private String day;
    private String sex;
    public boolean valid;
    
//    public String getYear() {
//    	return year;
//    }
//    
//    public void setYear(String yearSel) {
//    	year = yearSel;
//    }
//    
//    public String getMonth() {
//    	return month;
//    }
//    
//    public void setMonth(String monthSel) {
//    	month = monthSel;
//    }
//    
//    public String getDay() {
//    	return day;
//    }
//    
//    public void setDay(String daySel) {
//    	day = daySel;
//    }
    
    public String getSex() {
    	return sex;
    }
    
    public void setSex(String sexSel) {
    	sex = sexSel;
    }
	
	public String getMail() {
		return eMail;
	}
    
	public void setMail(String newEMail) {
		eMail = newEMail;
	}
    
//    public String getFirstName() {
//       return firstName;
//	}
//
//    public void setFirstName(String newFirstName) {
//       firstName = newFirstName;
//	}
//
//	
//    public String getLastName() {
//       return lastName;
//			}
//
//    public void setLastName(String newLastName) {
//       lastName = newLastName;
//			}
			

    public String getPassword() {
       return password;
	}

    public void setPassword(String newPassword) {
       password = newPassword;
	}
	
			
    public String getUsername() {
       return username;
			}

    public void setUsername(String newUsername) {
       username = newUsername;
			}

				
    public boolean isValid() {
       return valid;
	}

    public void setValid(boolean newValid) {
       valid = newValid;
	}	
}