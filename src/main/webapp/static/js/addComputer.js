$(document).submit(function(event) {
	var introduced = $("#introduced").val();
	var discontinued = $("#discontinued").val();
	//console.log("log : "+introduced);
	//console.log( introduced.length >1);
	
	//console.log("log : "+discontinued);
	console.log( discontinued.length );
	
	if (introduced.length > 1) {
		if (discontinued.length > 1) {
			if (new Date(introduced) > new Date(discontinued)) {
			document.getElementById("erroradd").innerHTML =
			"Date Interval not Valid ";
				event.preventDefault();
				$("#alert-message").show();
			}
		}
	}
	var name = $("#computerName").val();
	
	if (name == "null" | name.startsWith(" ")){
	
	
	document.getElementById("erroradd").innerHTML =
	"Computer Name not Valid ";
		event.preventDefault();
		$("#alert-message").show();
	}
	
	
	
});