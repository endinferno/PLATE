var txtSelfId = document.querySelector("input#txtSelfId");
var txtTargetId = document.querySelector("input#txtTargetId");
var btnRegister = document.querySelector("button#btnRegister");
var btnCall = document.querySelector("button#btnCall");
var localVideo = document.querySelector("video#localVideo");
var remoteVideo = document.querySelector("video#remoteVideo");
var lblFrom = document.querySelector("label#lblFrom");
var demoCanvas = document.querySelector("canvas#demoCanvas");
var btnRecordAudio = document.querySelector("button#btnRecordAudio");
// 聊天窗口
var txtMsg = document.querySelector("input#txtMsg");
var btnSend = document.querySelector("button#btnSend");
var mystatus = document.querySelector("label#audioVolumn");

var peer = null;
var localConn = null;
var localStream = null;
//连接到peerjs服务器的选项
var connOption = {
	//secure: true,
	host: 'www.plate.pub',
	port: 9000,
	path: '/',
	debug: 3
};
