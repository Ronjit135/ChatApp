<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href='<c:url value="Scripts\CSS\Login.css"></c:url>'>
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> --%>
<link rel="stylesheet"
	href='<c:url value="Scripts/Bootstrap/css/bootstrap.min.css"></c:url>'>
<link rel="stylesheet"
	href='<c:url value="Scripts/CSS/Register.css"></c:url>'>
<script
	src='<c:url value="Scripts/Bootstrap/jQuery/jquery_3-2-1.js"></c:url>'></script>
<script
	src='<c:url value="Scripts/Bootstrap/js/bootstrap.min.js"></c:url>'></script>
<script src='<c:url value="Scripts/JS/Register.js"></c:url>'></script>
<title>Chat App</title>
</head>
<body onload="onLoad();">
	<div class="container-fluid">
		<div class="row" id="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<form id="registerForm" action="Register" method="post">
					<div class="container">
						<h3>Register</h3>
						<label for="email"><b>Email</b></label> <input type="text"
							placeholder="Enter Email" id="email" name="email" required>
						<label for="password"><b>Password</b></label> <input
							type="password" placeholder="Enter Password" id="password"
							name="password" required> <label for="userName"><b>Username</b></label>
						<input type="text" placeholder="Enter Username" id="userName"
							name="userName" required>
						<button id="Validate" type="button" onclick="validate()">Register</button>
					</div>
				</form>
			</div>
			<div class="col-sm-4"></div>
		</div>
		<div id="messageDiv">
			<div id="myModal" class="modal">
				<div class="modal-content">
					<span class="close">&times;</span>
					<p id="message"></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>