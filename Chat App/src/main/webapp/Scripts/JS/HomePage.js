function onLoadFunction() {
	scrollChat();
	setInterval(function() {
		updateMessages();
	}, 500)
	/*setInterval(function() {
		updateStatus();
	}, 1000)*/
	/*setInterval(function() {
		updateOnlineContacts();
	}, 1000)*/
}

function scrollChat() {
	var chat = document.getElementById("chat");
	chat.scrollTop = chat.scrollHeight;
}

function sendMessage() {
	var message = document.getElementById("messageText").value;
	document.getElementsByName("messageText")[0].value = "";
	if (message != "") {
		send(message);
		scrollChat();
	}
}

function send(message) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.response);
		}
	};
	xmlhttp.open("POST", "SendMessage", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("messageText=" + message);
}

function updateMessages() {
	var message = document.getElementsByName("messageText");
	if (message.disabled) {
		update();
	}
}

function update() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			add(JSON.parse(this.response));
		}
	};
	xmlhttp.open("POST", "UpdateMessages", true);
	xmlhttp.send();
}

function add(data) {
	var userFrom = data.user[0].userFrom.userId;
	var userTo = data.user[0].userTo.userId;
	var msgs = data.msgs;
	for (var i = 0; i < msgs.length; i++) {
		var msg = msgs[i];
		addMessage(msg, userFrom, userTo)
	}
}

function addMessage(msg, from, to) {
	var msgId = msg.userFrom.userId;

	if (msg != null) {
		if (msgId == from) {
			var chat = document.getElementById("chat");
			var mainRow = document.createElement('div');
			mainRow.className = "row";
			var containerDarker = document.createElement('div');
			containerDarker.className = "container";
			containerDarker.className += " darker";
			var row = document.createElement('div');
			row.className = "row";
			var col1 = document.createElement('div');
			col1.className = "col-sm-2";
			var col2 = document.createElement('div');
			col2.className = "col-sm-8";
			var col3 = document.createElement('div');
			col3.className = "col-sm-2";
			var p = document.createElement('p');
			p.className = "wrapword";
			p.className += " right";
			var span = document.createElement('span');
			span.className = "time-right";
			var small = document.createElement('small');
			var messageNode = document.createTextNode(msg.messageText);
			var dateNode = document.createTextNode(msg.date);
			var imageNode = document.createElement('img');
			imageNode.setAttribute("src", "Images/Default_Avatar.png");
			imageNode.className = "right";
			p.append(messageNode);
			small.append(dateNode);
			span.append(small);
			col2.append(p);
			col2.append(span);
			col3.append(imageNode);
			row.append(col1);
			row.append(col2);
			row.append(col3);
			containerDarker.append(row);
			mainRow.append(containerDarker);
			chat.append(mainRow);
		}
		if (msgId == to) {
			var chat = document.getElementById("chat");
			var mainRow = document.createElement('div');
			mainRow.className = "row";
			var containerDarker = document.createElement('div');
			containerDarker.className = "container";
			var row = document.createElement('div');
			row.className = "row";
			var col1 = document.createElement('div');
			col1.className = "col-sm-2";
			var col2 = document.createElement('div');
			col2.className = "col-sm-8";
			var col3 = document.createElement('div');
			col3.className = "col-sm-2";
			var p = document.createElement('p');
			p.className = "wrapword";
			p.className += " left";
			var span = document.createElement('span');
			span.className = "time-left";
			var small = document.createElement('small');
			var messageNode = document.createTextNode(msg.messageText);
			var dateNode = document.createTextNode(msg.date);
			var imageNode = document.createElement('img');
			imageNode.setAttribute("src", "Images/Default_Avatar.png");
			imageNode.className = "left";
			p.append(messageNode);
			small.append(dateNode);
			span.append(small);
			col1.append(imageNode);
			col2.append(p);
			col2.append(span);
			row.append(col1);
			row.append(col2);
			row.append(col3);
			containerDarker.append(row);
			mainRow.append(containerDarker);
			chat.append(mainRow);
		}
		scrollChat();
	}
}

function updateStatus() {
	var userName = document.getElementById("userName").innerHTML;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log("Status Updated for " + userName + " : " + this.response);
		}
	};
	xmlhttp.open("POST", "UpdateStatus", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("userName=" + userName);
}

function updateOnlineContacts() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			updateContacts(this.response);
		}
	};
	xmlhttp.open("POST", "Users", true);
	xmlhttp.send();
}

function updateContacts(data){
	var users = JSON.parse(data);
	var userDiv = document.getElementById("users");
	while (userDiv.hasChildNodes()){
		userDiv.removeChild(userDiv.lastChild);
	}
	for (var i = 0; i < users.length; i++) {
		var user = users[i];
		
		var userNode = document.createElement("div");
		userNode.className = "container darker";
		var row = document.createElement("div");
		var col1 = document.createElement("div");
		var col2 = document.createElement("div");
		var col3 = document.createElement("div");
		col1.className = "col-sm-2";
		col2.className = "col-sm-8";
		col3.className = "col-sm-2";
		
		var img = document.createElement("img");
		img.setAttribute("src","Images/Default_Avatar.png");
		img.setAttribute("style","width:100%");
		img.className = "right";
		
		var p = document.createElement("p");
		p.className = "wrapword left";
		
		var userNameNode = document.createTextNode(user.userName);
		
		var select = document.createElement("p");
		select.className = "wrapword right";
		
		col1.append(img);
		p.append(userNameNode);
		col2.append(p);
		col3.append(select);
		row.append(col1);
		row.append(col2);
		row.append(col3);		
		userNode.append(row);
		userDiv.append(userNode);
	}
}
/*<div class="container darker">
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
</div>*/