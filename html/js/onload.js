function isMediaAvailable() {
	if (!navigator.mediaDevices ||
		!navigator.mediaDevices.getUserMedia) {
		console.log('webrtc is not supported!');
		alert("webrtc is not supported!");
		return false;
	}
	return true;
}

window.onload = function() {
	if (isMediaAvailable() == false)
		return;

	// 隐藏视频元素
	// $("#localVideo").hide()
	// $("#remoteVideo").hide()
	$("#dialog-confirm").hide();

	registerButtonInit();
	callButtonInit();
	canvasInit();
	audioVideoStart();

}
