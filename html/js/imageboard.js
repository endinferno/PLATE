var zr = zrender.init(document.getElementById('imageBoard'));
var group = new zrender.Group({
    slent:true
});
var position = [];
var mes = {
    type : 3,
    src : "",
    position:[]
};

var file = document.getElementById('file');
file.onchange = function() {
    var fileData = this.files[0];
    console.log(this.files[0]);
    var reader = new FileReader();
    reader.readAsDataURL(fileData);
    reader.onload = function(e) {
        var src = this.result;
        showImage(src);
        mes.type = 3;
        mes.src = src;
        sendMessage(
                peer,
                localConn,
                txtSelfId.value,
                txtTargetId.value,
                "redraw", mes);
    };
    file.setAttribute('type','text');
    file.setAttribute('type','file');
};

function showImage(src) {
    var data=[src];
    for(var i = 0;i<data.length;i++){
        var img = new zrender.Image({
            style: {
                image: data[i],
                width: 150,
                height: 150
            },
            position:[200,200],
            draggable: true
        });
        img.on('mousedown',function () {
            position.push({
                "x" : img.position[0],
                "y" : img.position[1]
            });
        });
        img.on('mouseup',function () {
            position.push({
                "x" : img.position[0],
                "y" : img.position[1]
            });
            mes.type = 4;
            mes.position = position;
            sendMessage(
                peer,
                localConn,
                txtSelfId.value,
                txtTargetId.value,
                "redraw", mes);
            position = [];
        });
    }
    group.add(img);
    zr.add(group);
}

function imageBoardProcess(msg) {
	if(msg.action === "redraw"){
        if (msg.data.type === 3) {
            //添加picture
            showImage(msg.data.src);
        }
        else if(msg.data.type === 4){
            group.eachChild(function (item) {
                if(item.position[0] === msg.data.position[0].x && item.position[1] === msg.data.position[0].y) {
                    item.attr({
                        position:[msg.data.position[1].x,msg.data.position[1].y],
                        // draggable: true
                    });
                }
           })
        }
        else if(msg.data.type === 5){
            group.removeAll();
            zr.flush();
            zr.painter.clear();
        }
	}
}

function clearImage() {
    group.removeAll();
    zr.flush();
    zr.painter.clear();
    mes.type = 5;
    sendMessage(
                peer,
                localConn,
                txtSelfId.value,
                txtTargetId.value,
                "redraw", mes);
}