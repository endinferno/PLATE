var startBtn = document.querySelector("button#btn1");
var stopBtn = document.querySelector("button#btn2");
var calBtn = document.querySelector("button#btn3");

function start() {
	startTime = new Date();
	console.log(startTime.getTime());
}

function stop() {
	stopTime = new Date();
	console.log(stopTime.getTime());

}

function getTimeDistance() {
	var timeDistance = stopTime.getTime() - startTime.getTime();
	//计算出相差天数
	var days = Math.floor(timeDistance / (24 * 3600 * 1000));
	//计算出小时数
	var leave1 = timeDistance % (24 * 3600 * 1000); //计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000));
	//计算相差分钟数
	var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000));
	//计算相差秒数
	var leave3 = leave2 % (60 * 1000); //计算分钟数后剩余的毫秒数
	var seconds = Math.round(leave3 / 1000);
	console.log(" 相差 " + days + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒");
}

