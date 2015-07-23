<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<!--  <meta http-equiv="refresh" content="30"> -->
<link rel="stylesheet" type="text/css" href="./CSS/index.css" />
<link rel="stylesheet" type="text/css" href="./CSS/homepage.css" />
<script type="text/javascript" src="JavaScript/jquery-1.10.2.js"></script>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>-->
<script type="text/javascript" src="./JavaScript/logout.js"></script>
<script type="text/javascript" src="./JavaScript/home.js"></script>
<title>UnChatting - HOME</title>
</head>
<body>
	<div class="header">
	<img alt="UnChatting" src="images/UnChatting3D.png"/>
		<div id="hl">
			<div id="menu" onclick="openmenu()"><%=session.getAttribute("username")%>
			</div>
			<div id="content_menu">
				<div onclick="modifyProfile()" class="btiv">Change Password</div>
				<div class="guide-section guide-header signup-promo guided-help-box">
					<div style="padding: 15px;">
						old password <input id="old_p" name="old_p" type="password"
							class="log_input" size="20">
					</div>
					<div style="padding: 15px;">
						new password <input id="new_p" name="new_p" type="password"
							class="log_input" size="20">
					</div>
					<div style="padding: 15px;">
						<input id="change_pw" name="change_p" type="button"
							class="log_input" value="Change Password"
							onclick="changePassword()">
					</div>
				</div>
				<div class="btiv" onclick="delAcc()">Delete Account</div>
				<div class="btiv" onclick="logOut()">Logout</div>
			</div>

		</div>
		<div id="hr">
			<div id="search">
				<input id="q" type="text" placeholder="search friends"
					autocomplete="off" name="q" maxlength="100"
					onkeyup="searchFriends()">
			</div>
			<div id="search_menu"></div>
		</div>
	</div>
	<div id="container">
		<div id="listchat" class="borderright">
			<div id="listchat_header" class="borderbottom pad05">Chat List</div>
			<div id="list_of_chat"></div>
		</div>

		<div id="main">

			<div id="header_chat">
				<span></span>
				<img src="images/delete.png" alt="close conversation" title="close conversation" onclick="closeChat()" />
			</div>
			<div id="current_chat"></div>
			<div id="foot_chat">
			<span id="newmess">&darr; new messages &darr;</span>
			<textarea id="textMessage" rows="3" role="textbox" placeholder="write a message..." onkeypress="enter_textarea(this,event)"></textarea>

		</div>

		</div>

		<div id="adrbk" class="borderleft">
			<div id="friendlist_header" class="borderbottom pad05">Friends List</div>

			<div id="user_book"></div>

			<div id="c_list">
				<%
					String u = String.valueOf(session.getAttribute("username"));
				%>
			</div>
		</div>
	</div>
	<div id="footer">
	<div>
		Powered By: <span class="contactus" title="matricola: 0610591">Antonino Buscetta</span> e <span class="contactus" title="matricola: 0610808">Luigi Roggio</span>
		<br/>
		<a href="http://apswpa.blogspot.it/" target="_blank">Architetture e Progetto dei Sistemi Web</a> - prof. Marco La Cascia
		<br />
		Unipa - Laurea Magistrale in Ingegeria Informatica - A.A. 2012/2013
	</div>
</body>
</html>