// 画布
var context = null;
var started = false;
// 用户输入缓冲区
var buffer = [];

function canvasInit() {
	console.log('Canvas Init');
	context = demoCanvas.getContext('2d');
	//canvas鼠标按下的处理
	demoCanvas.onmousedown = canvasMouseDownAction;
	//canvas鼠标移动的处理
	demoCanvas.onmousemove = canvasMouseMoveAction;
	//canvas鼠标抬起的处理
	demoCanvas.onmouseup = canvasMouseUpAction;
}

function canvasMouseDownAction(e) {
	e.preventDefault();
	context.strokeStyle = '#00f';
	context.beginPath();
	started = true;
	buffer.push({
		"x": e.offsetX,
		"y": e.offsetY
	});
}

function canvasMouseMoveAction(e) {
	if (started) {
		context.lineTo(e.offsetX, e.offsetY);
		context.stroke();
		buffer.push({
			"x": e.offsetX,
			"y": e.offsetY
		});
	}
}

function canvasMouseUpAction(e) {
	if (started) {
		started = false;
		//鼠标抬起时，发送坐标数据
		sendMessage(
			peer,
			localConn,
			txtSelfId.value,
			txtTargetId.value,
			"redraw", buffer);
		buffer = [];
	}
}

function canvasRegisterButtonProcess(msg) {
	if (msg.action === "redraw") {
		//还原canvas
		context.strokeStyle = '#f00';
		context.beginPath();
		context.moveTo(msg.data[0].x, msg.data[0].y);
		for (const pos in msg.data) {
			context.lineTo(msg.data[pos].x, msg.data[pos].y);
		}
		context.stroke();
	}
}
