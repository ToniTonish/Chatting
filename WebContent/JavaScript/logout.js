function logOut() {	
	$.ajax({
		type: "POST",
		url: "Access",
		data:
			{
				method: 3
			},
		success: function(data) {
			if ( data == "true")
			{
				window.location.replace("index.jsp");
			}
		}
	});
}