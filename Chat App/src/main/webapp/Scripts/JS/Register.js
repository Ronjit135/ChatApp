function onLoad() {
	document.getElementById("Validate").hidden = false;
	document.getElementById("Validate").disabled = false;
}

function validate() {
	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;
	var userName = document.getElementById("userName").value;
	if(email == ''){
		modal("Please Enter Email");
	} else if(password == ''){
		modal("Please Enter Password");
	} else if(userName == ''){
		modal("Please Enter Username");
	} else {
		validateData(email, password, userName);
	}
}

function validateData(email, password, userName) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			perform(this.response);
		}
	};
	xmlhttp.open("POST", "Validate", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("email=" + email + "&password=" + password + "&userName="
			+ userName);
	return true;
}

function perform(data) {
	var response = JSON.parse(data);
	var flag = false;
	var message = "";

	if (response.email != true) {
		flag = true;
		message += "Email already exists";
	}
	if (response.password != true) {
		flag = true;
		message += "Password";
	}
	if (response.userName != true) {
		flag = true;
		message += "Username";
	}
	if (flag) {
		modal(message);
	} else {
		if(response.register == true){
			var msg = "Registration Successfull";
			modal(msg,true);
		} else {
			var msg = "Registration Failed";
			modal(msg);
		}
	}

}

function modal(message, isConfirm = false){
	var modal = document.getElementById('myModal');
	var span = document.getElementsByClassName("close")[0];
	var messageBox = document.getElementById('message');
	messageBox.innerHTML = message;
	modal.style.display = "block";
	span.onclick = function() {
		modal.style.display = "none";
		if(isConfirm){
			window.location.href = "./";
		}
	}
}