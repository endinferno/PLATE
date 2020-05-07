var fs = require('fs');
//var PeerServer = require('peer').PeerServer;
const { PeerServer } = require('peer');

var options = {
	key: fs.readFileSync('https/3874880.key'),
	cert: fs.readFileSync('https/3874880.pem')
}

var server = PeerServer({
	port: 9000,
	ssl: options,
	path:'/',
});

//[>* 构建html页 <]
var https = require('https');
var http = require('http');
var serveIndex = require('serve-index');
var express = require("express");
////var htmlApp = express();
////htmlApp.use(serveIndex('./html'));
////htmlApp.use(express.static("./html"))
////var httpsServer = https.createServer(options, htmlApp);
////httpsServer.listen(81, "0.0.0.0");

//// Create service
var htmlApp = express();
htmlApp.use(serveIndex('./html'));
htmlApp.use(express.static("./html"))

let sever = http.createServer(function (req,res) {
  res.writeHead(301, {'Location':'https://www.plate.pub/'});
  res.end();
});

sever.listen(80,'0.0.0.0');
https.createServer(options, htmlApp).listen(443, '0.0.0.0');
