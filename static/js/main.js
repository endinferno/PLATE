"use strict";

var tempContext = null; // global variable 2d context
var canvas = null;
var started = false;
var x = 0,
	y = 0;
var connect_flag = true;

window.onload = function() {
	canvas = document.getElementById("canvas");
	// console.log(canvas.parentNode.clientWidth);
	canvas.width = canvas.parentNode.clientWidth;
	canvas.height = canvas.parentNode.clientHeight;
	if (!canvas.getContext) {
		console.log(
			"Canvas not supported. Please install a HTML5 compatible browser."
		);
		return;
	}
	// get 2D context of canvas and draw rectangel
	tempContext = canvas.getContext("2d");
	tempContext.fillStyle = "#fff";
	context.fillRect(0, 0, canvas.width, canvas.height);
	x = canvas.width / 2;
	y = canvas.height / 2;

	canvas.addEventListener("mousedown", doMouseDown, false);
	canvas.addEventListener("mousemove", doMouseMove, false);
	canvas.addEventListener("mouseup", doMouseUp, false);
	var interval = self.setInterval("clock()", 10);
};

function getPointOnCanvas(canvas, x, y) {
	var bbox = canvas.getBoundingClientRect();
	return {
		x: x - bbox.left * (canvas.width / bbox.width),
		y: y - bbox.top * (canvas.height / bbox.height),
	};
}

function doMouseDown(event) {
	var x = event.pageX;
	var y = event.pageY;
	var canvas = event.target;
	var loc = getPointOnCanvas(canvas, x, y);
	// console.log("mouse down at point( x:" + loc.x + ", y:" + loc.y + ")");
	tempContext.beginPath();
	tempContext.moveTo(loc.x, loc.y);
	started = true;
}

function doMouseMove(event) {
	var x = event.pageX;
	var y = event.pageY;
	var canvas = event.target;
	var loc = getPointOnCanvas(canvas, x, y);
	if (started) {
		tempContext.lineTo(loc.x, loc.y);
		tempContext.stroke();
	}
}

function doMouseUp(event) {
	// console.log("mouse up now");
	if (started) {
		doMouseMove(event);
		started = false;
	}
}

function clock() {
	if (connect_flag == true) {
		if (canvas != null) {
			var img_json = {
				baseimg: canvas.toDataURL("image/jpeg")
			};
			$.ajax({
				url: "/sendData",
				cache: false,
				data: JSON.stringify(img_json),
				contentType: "application/json",
				mimetype: "application/json",
				dataType: "json",
				type: "POST",
				processData: false,
				success: function(e) {
					// 可以返回数据库当前状态的图像，以便用户更新同步操作
					console.log("Success");
				},
				error: function() {
					console.log("Error");
				},
			});
		}
	}
}
