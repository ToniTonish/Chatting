function signUp() 
{
	var usrnm = $('#reg_u').val();
	var email = $('#reg_em').val();
	var psw = $('#reg_p1').val();
	var sex = $('input:radio[name=sex]:checked').val();
	
	$.ajax({
		type: "POST",
		url: "Access",
		data:
			{
				username: usrnm,
				password: psw,
				email: email,
				sex: sex,
				method: 1
			},
		success: function(data) {
			if ( data == "false")
			{
				$(".error_box").show();
			}
			else if ( data == "true")
			{
				window.location.replace("homepage.jsp");
			}
		}
	});
}

function logIn() {
	var usrnm = $('#un').val();
	var psw = $('#pw').val();
	var kl = document.getElementById("kl").checked;
	$.ajax({
		type: "POST",
		url: "Access",
		data:
			{
				username: usrnm,
				password: psw,
				kl: kl,
				method: 2
			},
		success: function(data) {
			if ( data == "false")
			{	
				window.location.assign("invalidLogin.jsp");
			}
			else if ( data == "true")
			{
				window.location.replace("homepage.jsp");
			}
		}
	});
}

function enterLogin(evento) {
	codice_tasto = evento.keyCode ? evento.keyCode : evento.which ? evento.which : evento.charCode;
	if (codice_tasto == 13) {
		logIn();
	}
}

function passSession(id,usn) {
	$.ajax({
		type: "POST",
		url: "Access",
		data:
			{
				idS: id,
				un: usn,
				method: 2
			},
		success: function(data) {
			if ( data == "false")
			{
				$(".error_box").show();
			}
			else if ( data == "true")
			{
				window.location.replace("homepage.jsp");
			}
		}
	});
}