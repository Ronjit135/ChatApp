<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<link rel="stylesheet"
	href='<c:url value="Scripts/Bootstrap/css/bootstrap.min.css"></c:url>'>
<link rel="stylesheet"
	href='<c:url value="Scripts/CSS/HomePage.css"></c:url>'>
<script
	src='<c:url value="Scripts/Bootstrap/jQuery/jquery_3-2-1.js"></c:url>'></script>
<script
	src='<c:url value="Scripts/Bootstrap/js/bootstrap.min.js"></c:url>'></script>
<script src='<c:url value="Scripts/JS/HomePage.js"></c:url>'></script>
<script src="https://use.fontawesome.com/45e03a14ce.js"></script>
<title>Home</title>
</head>
<body onload="onLoadFunction();">
	<div class="fullContainer">
		<div class="row" style="height: 100px">
			<div class="col-sm-10"></div>
			<div class="col-sm-1" style="text-align: right;" id="userName">${user.userName}</div>
			<div class="col-sm-1" style="text-align: left;">
				<a href="Logout">Logout</a>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<!-- Users -->
			<div class="col-sm-4" id="users">
				<c:forEach var="user" items="${userList}">
					<div class="container darker">
						<div class="row">
							<div class="col-sm-2">
								<img src='<c:url value="Images/Default_Avatar.png"></c:url>'
									class="right" style="width: 100%;">
							</div>
							<div class="col-sm-8">
								<p class="wrapword left">${user.userName}</p>
							</div>
							<div class="col-sm-2">
								<p class="wrapword right">
									<a href="Chat?email=${user.email}">Select</a>
								</p>
							</div>

						</div>
					</div>
				</c:forEach>
			</div>
			<!-- Chat -->
			<div class="col-sm-6" id="chat">
				<c:if test="${showNoMsg}">
					<div class="row">
						<div class="col-sm-12" id="blankDiv">Select Contact to View
							Messages</div>
					</div>
				</c:if>
				<c:if test="${!showNoMsg}">
					<c:forEach var="msg" items="${msgList}">
						<div class="row">
							<c:if test="${user.userId == msg.userFrom.userId}">
								<div class="container darker">
									<div class="row">
										<div class="col-sm-2"></div>
										<div class="col-sm-8">
											<p class="wrapword right">${msg.messageText}</p>
											<span class="time-right"><small>${msg.date}</small></span>
										</div>
										<div class="col-sm-2">
											<img src='<c:url value="Images/Default_Avatar.png"></c:url>'
												class="right" style="width: 100%;">
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${user.userId != msg.userFrom.userId}">
								<div class="container">
									<div class="row">
										<div class="col-sm-2">
											<img src='<c:url value="Images/Default_Avatar.png"></c:url>'
												style="width: 100%;">
										</div>
										<div class="col-sm-8">
											<p class="wrapword left">${msg.messageText}</p>
											<span class="time-left"><small>${msg.date}</small></span>
										</div>
										<div class="col-sm-2"></div>
									</div>
								</div>
							</c:if>
						</div>
					</c:forEach>
				</c:if>
			</div>
			<div class="col-sm-1"></div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-4" id="messageBox"></div>
			<div class="col-sm-6" id="messageBox">
				<c:if test="${showNoMsg}">
					<form action="SendMessage" method="post">
						<div class="input-group">
							<textarea class="form-control custom-control" rows="2"
								style="resize: none" id="messageText" name="messageText"
								placeholder="Enter Message to Send..." disabled="disabled"></textarea>
							<input type="button" class="btn btn-primary" value="Send"
								onclick="sendMessage();" disabled="disabled">
						</div>
					</form>
				</c:if>
				<c:if test="${!showNoMsg}">
					<form action="SendMessage" method="post">
						<div class="input-group">
							<textarea class="form-control custom-control" rows="2"
								style="resize: none" id="messageText" name="messageText"
								placeholder="Enter Message to Send..."></textarea>
							<input type="button" class="btn btn-primary" value="Send" onclick="sendMessage();">
						</div>
					</form>
				</c:if>
			</div>
			<div class="col-sm-1"></div>
		</div>
	</div>
</body>
</html>