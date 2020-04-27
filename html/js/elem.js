var txtSelfId = document.querySelector("input#txtSelfId");
var txtTargetId = document.querySelector("input#txtTargetId");
var btnRegister = document.querySelector("button#btnRegister");
var btnCall = document.querySelector("button#btnCall");
var localVideo = document.querySelector("video#localVideo");
var remoteVideo = document.querySelector("video#remoteVideo");
var lblFrom = document.querySelector("label#lblFrom");
var demoCanvas = document.querySelector("canvas#demoCanvas");
var btnRecordAudio = document.querySelector("button#btnRecordAudio");

var peer = null;
var localConn = null;
var localStream = null;
//连接到peerjs服务器的选项
var connOption = {
	host: 'localhost',
	port: 9000,
	path: '/',
	debug: 3
};
