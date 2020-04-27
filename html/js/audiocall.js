var recordedChunks = []
var mediaRecorder;
var audioCtx;

function visualize(stream) {
	if (!audioCtx)
		audioCtx = new AudioContext();
	const source = audioCtx.createMediaStreamSource(stream);

	const analyser = audioCtx.createAnalyser();
	analyser.fftSize = 2048;
	const bufferLength = analyser.frequencyBinCount;
	const dataArray = new Uint8Array(bufferLength);

	source.connect(analyser);
}

// 获取流
function gotStream(stream) {
	console.log('received local stream');
	localStream = stream;
	localVideo.srcObject = localStream;
	mediaRecorder = new MediaRecorder(stream);
	visualize(stream)
	console.log(mediaRecorder);
	mediaRecorder.ondataavailable = function(e) {
		recordedChunks.push(e.data)
	}
	mediaRecorder.onstop = function(e) {
		console.log('audio stop');
		// TODO: audio 名字待定
		let audioName = '123';
		const audioBlob = new Blob(recordedChunks, {
			type: "video/mp4"
		});
		// TODO: 传输到后台保存
		recordedChunks = []
		mediaRecorder.start();
	}
	mediaRecorder.start();

}
//开启本地音频设备
function audioVideoStart() {
	if (localStream) {
		localStream.getTracks().forEach(track => {
			track.stop();
		});
	}
	const constraints = {
		audio: true,
		video: true
	};
	navigator.mediaDevices
		.getUserMedia(constraints)
		.then(gotStream)
		.catch(handleError);
}
// 注册按键的事件
function audioVideoRegisterButtonAction() {
	if (!peer) {
		if (txtSelfId.value.length == 0) {
			alert("please input your name");
			txtSelfId.focus();
			return;
		}
		peer = new Peer(hashCode(txtSelfId.value), connOption);
		peer.on('open', function(id) {
			console.log("register success. " + id);
		});
		peer.on('call', function(call) {
			call.answer(localStream);
		});
		peer.on('connection', (conn) => {
			conn.on('data', (data) => {
				var msg = JSON.parse(data);
				console.log(msg);
				//收到邀请时，弹出询问对话框
				if (msg.action === "call") {
					lblFrom.innerText = msg.from;
					txtTargetId.value = msg.from;
					// 询问窗口
					$("#dialog-confirm").dialog({
						resizable: false,
						height: "auto",
						width: 400,
						modal: true,
						buttons: {
							"Accept": function() {
								$(this).dialog(
									"close");
								sendMessage(
									peer,
									localConn,
									msg.to,
									msg.from,
									"accept",
									null);
							},
							Cancel: function() {
								$(this).dialog(
									"close");
							}
						}
					});
				}

				//接受视频通话邀请
				if (msg.action === "accept") {
					console.log("accept call => " + JSON
						.stringify(msg));
					var call = peer.call(hashCode(msg
						.from), localStream);
					call.on('stream', function(stream) {
						console.log(
							'received remote stream'
						);
						remoteVideo.srcObject =
							stream;
						sendMessage(
							peer,
							localConn,
							msg.to, msg.from,
							"accept-ok", null);
					});
				}

				//接受视频通话邀请后，通知另一端    
				if (msg.action === "accept-ok") {
					console.log("accept-ok call => " + JSON
						.stringify(msg));
					var call = peer.call(hashCode(msg
						.from), localStream);
					call.on('stream', function(stream) {
						console.log(
							'received remote stream'
						);
						remoteVideo.srcObject =
							stream;
					});
				}
				canvasRegisterButtonProcess(msg);
			});
		});
	}
}
// call按键的事件
function audioVideoCallButtonAction() {
	if (txtTargetId.value.length == 0) {
		alert("please input target name");
		txtTargetId.focus();
		return;
	}
	sendMessage(
		peer,
		localConn,
		txtSelfId.value,
		txtTargetId.value,
		"call", null);
}
