var tdBox = document.querySelector("td#tdBox");

function infoSendButtonAction() {
	if (txtTargetId.value.length == 0) {
		alert("please input target name");
		txtTargetId.focus();
		return;
	}
	if (txtMsg.value.length == 0) {
		alert("please input message");
		txtMsg.focus();
		return;
	}
	//发送消息
	sendMessage(
		peer,
		localConn,
		txtSelfId.value,
		txtTargetId.value,
		"message",
		txtMsg.value
	);
}

function messageProcess(msg) {
	if (msg.action == 'message') {
		tdBox.innerHTML = tdBox.innerHTML += "<div class='align_right'>" + msg.from +
			" : " + msg.data + "</div>";
		if (txtTargetId,value.length = 0) {
			txtTargetId.value = msg.from;
		}
	}
}
