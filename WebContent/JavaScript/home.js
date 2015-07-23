// Global Variables
var t2 = null;
var xtimeout2 = false;
var ownUsername = null;
var SFVAL = "";
var LASTSCROLLTOP = 0;
var DELTASCROLL = 0;
var TYPEUC = null;
var elem1 = null;


// Functions
function getFriendList() {
	var xml = postXML("FriendList");
	var xsl = postXML("XSL/FriendList.xsl");
	var list = displayResult(xml, xsl);
	$("#user_book").empty();
	$("#user_book").append(list);
	checkNotify();
}

function getChatList() {
	var xml = postXML("ChatList");
	var xsl = postXML("XSL/ChatList.xsl");
	var list = displayResult(xml, xsl);
	$("#list_of_chat").empty();
	$("#list_of_chat").append(list);
	var currRecId = $('#header_chat').attr("id_user");
	checkNotify(currRecId);
	updateChatList();
}

function updateChatList() {
	if (!xtimeout2) {
		xtimeout2 = true;
		// Update List of Chat every 5 seconds
		t2 = setInterval("getChatList()", 5000);
	}
}

function closeChat() {
	LASTSCROLLTOP = 0;
	DELTASCROLL = 0;
	$('#header_chat').removeAttr("id_user");
	$('#header_chat > span').text("");
	$("#current_chat").empty();
	$('#main').hide();
}

function setCss() {
	$('div.message_container[user='+ownUsername+']').each(function(){
		   $(this).addClass('me');
		});
	$('div.message_container[user!='+ownUsername+']').each(function(){
		   $(this).addClass('other');
		});
}

$(document).ready(function() {
	ownUsername = $("#menu").text();
	getFriendList();
	getChatList();
	$('body').click(function(){
		$('#search_menu').empty();
	});
});

function openmenu() {
	$(".guide-section.guide-header.signup-promo.guided-help-box").hide(); /*aggiunto*/
	$('#content_menu').slideToggle("medium");
}

function searchFriends() {
	var search_bar = $('#q').val();
	if (search_bar != SFVAL) {
		SFVAL = search_bar;
		$("#search_menu").empty();
		if (search_bar != "" ) {
			var xml = getXML("SearchServlet?un=" + search_bar);
			var xsl = getXML("XSL/SearchFriends.xsl");
			var list = displayResult(xml, xsl);
			$("#search_menu").append(list);
		}
	}
}

function getXML(serv) {
	xhttp = new XMLHttpRequest();
	xhttp.open("POST", serv, false);
	xhttp.send("");
	return xhttp.responseXML;
}

function postXML(serv) {
	var outerData = null;
	$.ajax({
	  type: "POST",
	  url: serv,
	  async: false,
	  cache: true,
	  dataType: "xml",
	  success : function(data) {
	    outerData = data;
	  }
	});
	return outerData;
}

function getXSL(file) {
	$.ajax({
        type: "POST",
        url: file,
        dataType: "XSL",
        cache: true,
        success: function(result) {
              return result;
              },      
          async: true
        });
}

function displayResult(xml, xsl) {
	   xsltProcessor = new XSLTProcessor();
	   xsltProcessor.importStylesheet(xsl);
	   return xsltProcessor.transformToFragment(xml, document);
}

function addFriend(elem) {
	var userId = $(elem).parent().attr('id_user');
	var type = 1;
	friendRequest(userId, type);
}

function friendRequest(idUser, method) {
	$.ajax({
		type: "POST",
		url: "AddFriends",
		data:
			{
				idUser2: idUser,
				method: method,
			},
        success: function(result) {
        	getFriendList();
        },      
          async: true
        });
}

function openChat(elem,type,insert) {
	var user;
	var u_id;
	TYPEUC = type;
	if (TYPEUC == 1) {
		u_id = $(elem).parent().attr("id_user");
		user = $(elem).parent().text();
	} else {
		u_id = $(elem).attr("id_user");
		user = $(elem).children(".chatlist_name").text();
	}
	var xml = null;
	if (user != $("#header_chat > span").text()) {
		closeChat();
		$("#header_chat > span").empty();
		$("#header_chat > span").text(user);
		$("#header_chat").attr("id_user", u_id);
	}
	
	$("#main").show();
	
	$.ajax({
	  type: "POST",
	  url: "OpenConversation",
	  async: false,
	  data:
			{
				userIdR: u_id,
			},
	  cache: false,
	  dataType: "xml",
	  success : function(data) {
	    if(data != false) {
	    	xml = data;
			}
	    }
	});
	if (xml != null) {
		var xsl = getXML("XSL/Conversation.xsl");
		var list = displayResult(xml, xsl);
		$("#list_of_chat > div[id_user="+u_id+"]").children("img").attr("src","images/notifyOff.png").attr("title","No new message").attr("alt", "No new message");
		$("#current_chat").empty();
		$("#current_chat").append(list);
		$('#current_chat').scroll(function(event) {
			var currentScroll = $(this).scrollTop();
			DELTASCROLL = Math.abs(LASTSCROLLTOP - currentScroll);
		});
		if (DELTASCROLL < 50) {
			$("#current_chat").scrollTop($("#current_chat")[0].scrollHeight);
			LASTSCROLLTOP = $("#current_chat").scrollTop();
		} else if (insert != true){
			$('span#newmess').css('visibility','visible');
			setTimeout("$('span#newmess').css('visibility','hidden');",5000);
		}
	}
	elem1 = elem;
//	updateChat();
	setCss();
}

function modifyProfile() {
	$(".guide-section.guide-header.signup-promo.guided-help-box").slideToggle("medium");
}

function changePassword() {
	var patt=/^[a-zA-Z1-9._-]{4,32}$/i;
	var oldPwd = $("#old_p").val();
	var newPwd = $("#new_p").val();
	if (patt.test(newPwd) && newPwd != oldPwd) {
		$.ajax({
			type: "POST",
			url: "ChangePassword",
			data:
			{
				oldPassword: oldPwd,
				newPassword: newPwd,
			},
			success: function(data) {
				if ( data == "false") {
					$(".error_box").show();
				} else if ( data == "true") {
					alert("ok cambiata");
				}
			},      
			async: true
		});
	}
	else
		alert("new password is invalid!");
}

function enter_textarea(campo,evento) {
	var textMsg = $("#textMessage").val();
	codice_tasto = evento.keyCode ? evento.keyCode : evento.which ? evento.which : evento.charCode;
	if (codice_tasto == 13) {
		if (evento.shiftKey === true) {
			return true;
		} else {
			evento.preventDefault();
			var user_id = $(campo).parent().siblings('#header_chat').attr("id_user");

			$.ajax({
				type: "POST",
				url: "TextMessage",
				async: true,
				data:
				{
					userIdR: user_id,
					textMessage: textMsg,
				},
				cache: true,
				success: function(result) {
					$("#textMessage").val('');
					$("#user_book > div > div[attribute='"+user_id+"']").click ();
					$("#current_chat").scrollTop($("#current_chat")[0].scrollHeight);
					openChat(elem1,TYPEUC,true);
				},
			});
		}
	} else {
		return true;
	}
}

function checkNotify(currRecId) {
	$.ajax({
		type: "POST",
		url: "Notification",
		async: false,
		cache: true,
		success: function(data) {
			if ( data != "false") {
				var res = data.split(";");
				for (i in res) {
					var index = res[i];
					if(currRecId != index) {
						// display notification
						$("#list_of_chat > div[id_user="+index+"]").children("img").attr("src","images/notify.png").attr("title","New Message").attr("alt", "New Message");
					} else {
						// update chat
						openChat(elem1,TYPEUC,false);
					}
				}
			}
		},
	});
}

function delAcc() {
	var c = confirm("are you sure you want to delete your account?");
	if (c == true) {
		$.ajax({
			type: "POST",
			url: "Access",
			data:
			{
				method: 4,
			},
			success: function(data) {
				if ( data == "false")
				{	
					alert("there was a problem in deleting your account.");
				}
				else if ( data == "true")
				{
					window.location.replace("index.jsp");
				}
			}
		});
	}
}

function removeFriend(elem) {
	var userId = $(elem).parent().attr("id_user");
	var type = -1;
	friendRequest(userId, type);
}

function openC(elem,type) {
	var u_id = 0;
	if (type == 0) {
		u_id = $(elem).attr("id_user");
	} else if (type == 1){
		u_id = $(elem).parent().attr("id_user");
	}
	var openchat_id = $('#header_chat').attr('id_user');
	if (u_id == openchat_id) {
		$("#current_chat").scrollTop($("#current_chat")[0].scrollHeight);
		return;
	} else {
		openChat(elem,type,false);
	}
}