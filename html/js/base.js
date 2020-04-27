// 获得用户ID
// TODO: 还需要修改，使用用户的UUID
hashCode = function(str) {
	var hash = 0;
	if (str.length == 0) return hash;
	for (i = 0; i < str.length; i++) {
		char = str.charCodeAt(i);
		hash = ((hash << 5) - hash) + char;
		hash = hash & hash;
	}
	return hash;
}
// P2P 发送数据
function sendMessage(peer, conn, from, to, action, data) {
	if (peer == null) {
		return;
	}
	if (from.length == 0 || to.length == 0) {
		return;
	}
	var message = {
		"from": from,
		"to": to,
		"action": action,
		"data": data
	};
	if (!conn) {
		conn = peer.connect(hashCode(to));
		conn.on('open', () => {
			conn.send(JSON.stringify(message));
			console.log(message);
		});
	}
	if (conn.open) {
		conn.send(JSON.stringify(message));
		console.log(message);
	}
}
// 处理异常
function handleError(err) {
	console.log('Error Occurred : ', err.message, err.name);
}
