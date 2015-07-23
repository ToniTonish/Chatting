<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="CSS/index.css" />
<script type="text/javascript" src="JavaScript/jquery-1.10.2.js"></script>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>-->
<script type="text/javascript" src="JavaScript/valid.js"></script>
<script type="text/javascript" src="JavaScript/ajaxRequest.js"></script>
<%
	Object logged = request.getSession(false).getAttribute("Logged");
	if (logged != null && logged.equals(true)) {
		response.sendRedirect("homepage.jsp");
	}
	Object keepLogIn = request.getSession(false).getAttribute("keepLogIn");
	if (keepLogIn != null && keepLogIn.equals(true)) {
		out.print("<script type=\"text/javascript\">");
		out.print("$(document).ready(function(){document.getElementById(\"kl\").checked=true;});");
		out.print("</script>");
	}
%>

<title>UnChatting - Sign Up to chat with your friends</title>
</head>
<body>
	<div class="header">
		<img alt="UnChatting" src="images/UnChatting3D.png"/>
		<div id="login">
			<form>
				<table id="log_table">
					<tr>
						<td><input id="un" name="un" type="text" class="log_input"
							size="15" placeholder="username" tabindex="1"></td>
						<td><input id="pw" name="pw" type="password"
							class="log_input" size="15" placeholder="password" tabindex="2" onkeyup="enterLogin(event)"></td>
						<td><input type="button" class="log_input" id="log_btn"
							value="Log In" onclick="logIn()" tabindex="4"></td>
					</tr>
					<tr>
						<td><input id="kl" name="kl" type="checkbox" tabindex="3"> 
						<label for="kl" id="keep_login">keep me logged in</label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="container">
		<div class="error_box" style="display: none;">
			<center>
				Invalid Sign Up.
				<p>Please choose your data carefully for the registration.</p>
			</center>
		</div>
		<div id="main">
			<div id="reg">
				<form id="reg_form">
					<table id="form_t">
						<caption>
							<h1>Sign Up</h1>
						</caption>
						<tr>
							<td><input id="reg_u" name="username" type="text" placeholder="Username"
								onblur="checkUsername()" /></td>
							<td><input id="reg_em" name="email" type="text" placeholder="Email"
								onblur="checkEMail()" /></td>
						</tr>
						<tr class="err">
							<td><span class="error hidden" id="span_username_r">This field
									is required!</span></td>
							<td><span class="error hidden" id="span_email_r">This field
									is required!</span></td>
						</tr>
						<tr>
							<td><input id="reg_p1" name="password" type="password"
								placeholder="Password" required="required"
								onblur="checkPassword()" onchange="checkRePassword()"/></td>
							<td><input id="reg_p2" type="password"
								placeholder="Confirm Password" onkeyup="checkRePassword()" required="required" /></td>
						</tr>
						<tr class="err">
							<td><span class="error hidden" id="span_password_r">This field is required!</span></td>
							<td><span class="error hidden" id="span_password2_r">This field is required!</span></td>
						</tr>
						<tr>
							<td colspan="2" class="center"><input id="reg_gm"
								type="radio" name="sex" value="Male" checked="checked" /><label
								for="reg_gm" class="l_clk">Male</label> <input id="reg_gf"
								type="radio" name="sex" value="Female" /><label for="reg_gf"
								class="l_clk">Female</label></td>
						</tr>
						<tr>
							<td colspan="2" class="center">
							<input id="sub_b" onmouseover="validate()" onfocus="validate()" type="button" onClick="signUp()" value="Register"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>