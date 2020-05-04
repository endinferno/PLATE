// 画布
var context = null;
var started = false;
// 用户输入缓冲区
var buffer = [];

var color = "#000000";
var lastColor = "#000000";
var fontSize = 2;
var isEraser = false;
var alpha = 1;
var data = {
	type : 1,
	buffer :[],
	color :"#000000",
	fontSize:2,
	alpha:1
};

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
	context.strokeStyle = color;
	context.lineWidth = fontSize;
	context.globalAlpha= alpha;
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
			"y": e.offsetY,
		});
	}
}

function canvasMouseUpAction(e) {
	if (started) {
		started = false;
		data.alpha = alpha;
		data.buffer = buffer;
		data.color = color;
		data.fontSize = fontSize;
		data.type = 1;
		//鼠标抬起时，发送坐标数据
		sendMessage(
			peer,
			localConn,
			txtSelfId.value,
			txtTargetId.value,
			"redraw", data);
		buffer = [];
	}
}

function canvasRegisterButtonProcess(msg) {
	if (msg.action === "redraw") {
		if(msg.data.type === 1) {
			//还原canvas
			console.log()
			context.lineWidth = msg.data.fontSize;
			context.strokeStyle = msg.data.color;
			if (msg.data.alpha < 1)
				msg.data.alpha += 0.35;
			context.globalAlpha = msg.data.alpha;
			context.beginPath();
			context.moveTo(msg.data.buffer[0].x, msg.data.buffer[0].y);
			for (const pos in msg.data.buffer) {
				context.lineTo(msg.data.buffer[pos].x, msg.data.buffer[pos].y);
			}
			context.stroke();
		}
		else if(msg.data.type === 2){
			context.globalAlpha = 1;
			context.fillStyle="#ffffff";
			context.fillRect(0,0,1000,1000);
		}
	}
}

function changePenColor(penColor) {
	if(!isEraser) {
		color = penColor;
	}
	lastColor = penColor;
}

function changePenWidth(width) {
	fontSize = width;
}

function clearAll() {
	context.globalAlpha = 1;
	context.fillStyle="#ffffff";
	context.fillRect(0,0,1000,1000);
	context.globalAlpha = alpha;
	data.type = 2;
	sendMessage(
			peer,
			localConn,
			txtSelfId.value,
			txtTargetId.value,
			"redraw", data);
}

function changeToPencil() {
	isEraser = false;
	color = lastColor;
	alpha = 1;
}

function changeToScrub() {
	isEraser = false;
	color = lastColor;
	alpha = 0.05;
}

function changeToEraser() {
	isEraser = true;
	lastColor = color;
	alpha = 1;
	color = "#ffffff";
}

function saveCanvas() {
	let image =  new Image();
 	image.src =  demoCanvas.toDataURL({format: 'image/jpg', quality:1, width:600, height:240});
 	var url = image.src.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
 	window.open(url);
}
