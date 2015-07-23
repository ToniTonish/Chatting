var validEmail = false;
var validUsername = false;
var validPassword = false;
var validRePassword = false;

$(document).ready(function (){
	$("#sub_b").prop("disabled", true);
});

function checkEMail() {
	
	var patt=/^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i;
	var emailMatch = $("#reg_em").val();
	var r = $("#span_email_r");
	
	if (patt.test(emailMatch))
	{
		r.removeClass("shown").addClass("hidden");
		validEmail = true;
	}
	else if($.trim(emailMatch) == "")
	{
		r.removeClass("hidden").addClass("shown");
		r.text("This field is required!");
		validEmail = false;
	}
	else
	{
		r.removeClass("hidden").addClass("shown");
		r.text("Please enter a valid email.");
		validEmail = false;
	}
	validate();
}


function checkUsername() {
	
	var patt=/^[0-9a-zA-Z._-]{2,15}$/i;
	var nameMatch = $("#reg_u").val();
	
	var r = $("#span_username_r");
	
	if (patt.test(nameMatch))
	{
		r.removeClass("shown").addClass("hidden");
		validUsername = true;
	}
	else if($.trim(nameMatch) == "")
	{
		r.text("This field is required!");
		r.removeClass("hidden").addClass("shown");
		validUsername = false;
	}
	else
	{
		r.text("Please enter a valid username.");
		r.removeClass("hidden").addClass("shown");
		validUsername = false;
	}
	validate();
}

function checkPassword() {
	
	var patt=/^[a-zA-Z1-9._-]{4,32}$/i;
	var nameMatch = $("#reg_p1").val();
	var r = $("#span_password_r");
	
	
	if (patt.test(nameMatch))
	{
		r.removeClass("shown").addClass("hidden");
		validPassword = true;
	}
	else if($.trim(nameMatch) == "")
	{
		r.text("This field is required!");
		r.removeClass("hidden").addClass("shown");
		validPassword = false;
	}
	else
	{
		r.text("Please enter a valid password.");
		r.removeClass("hidden").addClass("shown");
		validPassword = false;
	}
	validate();
}

function checkRePassword() {	
	var r = $("#span_password2_r");
	
	if ($("#reg_p1").val() == $("#reg_p2").val())
	{
		r.removeClass("shown").addClass("hidden");
		validRePassword = true;
	}
	else if($.trim($("#reg_p2").val()) == "")
	{
		r.text("This field is required!");
		r.removeClass("hidden").addClass("shown");
		validRePassword = false;
	}
	else
	{
		r.text("Please check your password.");
		r.removeClass("hidden").addClass("shown");
		validRePassword = false;
	}
	validate();
}

function validate() {
//	alert("validate");
	if (validEmail && validUsername && validPassword && validRePassword)
	{
		$("#sub_b").prop("disabled", false);
	} else {
		$("#sub_b").prop("disabled", true);
	}
}
