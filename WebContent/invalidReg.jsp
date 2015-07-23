<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="text/css" href="CSS/index.css" />
<link rel="stylesheet" type="text/css" href="CSS/invalidLogin.css" />
<title>UnChatting - Sign Up to chat with your friends</title>
</head>
<body>
	<div class="header">
		<div id="login">
			<form action="LoginServlet">
				<input id="un" name="un" type="text" class="log_input" size="12"
					placeholder="username"> <input id="pw" name="pw"
					type="password" class="log_input" size="12" placeholder="password">
				<input type="submit" class="log_input" id="log_btn" value="Log In">
			</form>
		</div>
	</div>
	<div id="container">
		<div class="error_box">
			<center>
				Invalid data for Sign Up.
				<p>
					Please choose your data carefully for the registration.
				</p>
			</center>
		</div>
		<div id="main">
			<div id="reg">
				<form id="reg_form" action="RegServlet" method="post">
					<table id="form_t">
						<caption>
							<h1>Sign Up</h1>
						</caption>
						<tr>
							<td><input id="reg_u" name="username" type="text" placeholder="Username"
								onblur="checkUsername()" /></td>
							<td><input id="reg_em1" name="email" type="text" placeholder="Email"
								onblur="checkEMail()" /></td>
						</tr>
						<tr class="err">
							<td><span class="error" id="span_username_r">This field
									is required!</span> <span class="error" id="span_username_wf">Please
									enter a valid username.</span></td>
							<td><span class="error" id="span_email_r">This field
									is required!</span> <span class="error" id="span_email_wf">Please
									enter a valid email.</span></td>
						</tr>
						<tr>
							<td><input id="reg_p1" name="password" type="password"
								placeholder="Password" required="required"
								onblur="checkPassword()" onchange="checkRePassword()"/></td>
							<td><input id="reg_p2" type="password"
								placeholder="Confirm Password" onblur="checkRePassword()" required="required" /></td>
						</tr>
						<tr class="err">
							<td><span class="error" id="span_password_r">This
									field is required!</span> <span class="error" id="span_password_wf">Please
									enter a valid password.</span></td>
							<td><span class="error" id="span_password2_r">This
									field is required!</span> <span class="error" id="span_password2_wf">Please
									check your password.</span></td>
						</tr>
						<tr>
							<td colspan="2" class="center"><input id="reg_gm"
								type="radio" name="sex" value="Male" checked="checked" /><label
								for="gm" class="l_clk">Male</label> <input id="reg_gf"
								type="radio" name="sex" value="Female" /><label for="gf"
								class="l_clk">Female</label></td>
						</tr>
						<tr>
							<td colspan="2" class="center"><input id="sub_b"
								type="submit" value="Register"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>