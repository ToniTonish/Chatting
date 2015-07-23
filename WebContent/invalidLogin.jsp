<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">

<link rel="stylesheet" type="text/css" href="./CSS/index.css" />
<link rel="stylesheet" type="text/css" href="./CSS/invalidLogin.css" />
<script type="text/javascript" src="JavaScript/jquery-1.10.2.js"></script>
<script type="text/javascript" src="JavaScript/ajaxRequest.js"></script>
<title>Invalid Log In</title>
</head>

<body>
	<div class="contenuto">
		<div class="error_box">
			<center>
				Invalid username or password.
				<p>If you are not a registered user,
				please <a href="index.jsp">sign up</a> first.</p>
			</center>
		</div>
		<div id="log">
			<form>
				<table cellspacing="1">
				<tr>
					<td class="html7magic">username:</td>
					<td><input id="un" type="text" name="un" size="15"/></td>
				</tr>
				<tr>
					<td class="html7magic">password:</td>
					<td><input id="pw" type="password" name="pw" size="15"/></td>
				</tr>
				<tr>
					<td><input id="kl" name="kl" type="checkbox" tabindex="3"> 
					<label for="kl" id="keep_login">keep me logged in</label></td>
					<td colspan="2"><input type="button" onclick="logIn()" value="Log In"/></td>
				</tr>
			</table>
		</form>
		</div>
	</div>
</body>
</html>