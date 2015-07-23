var Month = {
	1 : "Jan",
	2 : "Feb",
	3 : "Mar",
	4 : "Apr",
	5 : "May",
	6 : "Jun",
	7 : "Jul",
	8 : "Aug",
	9 : "Sep",
	10 : "Oct",
	11 : "Nov",
	12 : "Dec"
};

//Rendiamo l'oggetto non più modificabile
Object.freeze(Month);


function populate(){
	var i;
	
	for (i=1; i<=12; i++) {
		$("select#month").append(new Option(Month[i],i));
	}
	
	for (i=1; i<= 31; i++) {
		$("select#day").append('<option value=' + i +'>' + i + '</option>');
	}
	
	var d = new Date();
	var curr_y = d.getFullYear();
	for (i=1; i<=100; i++) {
		$("select#year").append('<option value=' + curr_y +'>' + curr_y-- + '</option>');
	}
}

function popDay() {
	var m = $('select#month').val();
	switch (m){
	case '11':
	case '4':
	case '6':
	case '9':
		if ($("select#day option[value=30]").val() == undefined) {
			//Se non è presente il giorno 30 lo aggiungiamo
			$("select#day").append(new Option("30","30"));
		}
		
		if ($("select#day option[value=31]").val() == "31") {
			// Se è presente il giorno 31 lo eliminiamo
			$("select#day option[value=31]").remove();
		}
		break;
	
	case '2':
		//Febbraio -> eliminiamo i giorni 30 e 31
		for (var i=30; i<=31; i++){
			$("select#day option[value=" + i + "]").remove();
		}
		break;
	
	default:
		if ($("select#day option[value=30]").val() == undefined) {
			$("select#day").append(new Option("30","30"));
		}
		if ($("select#day option[value=31]").val() == undefined) {
			$("select#day").append(new Option("31","31"));
		}
		break;
	}
}