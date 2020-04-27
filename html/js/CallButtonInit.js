// call按键处理
function callButtonInit() {
	btnCall.onclick = function() {
		audioVideoCallButtonAction();
	}
}
// record按键处理
function recorderButtonInit() {
	btnRecordAudio.onclick = function() {
		mediaRecorder.stop();
	}
}
