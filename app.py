# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify, render_template, jsonify, json, Response
from Image import Image


app = Flask(__name__)  # 实例化app对象
image_list = Image()


@app.route('/')
def hello_world():
    return render_template('index.html')


@app.route('/sendData', methods=['POST'])
def form_data():
    # print(request.remote_addr)
    if request.method == 'POST':
        image_list.push((request.remote_addr, request.get_json()['baseimg']))
        # print(request.get_json()['baseimg'])
        print(image_list.size())
        return jsonify(status="success", data='123')

def gen(img_list):
    while True:
        image = img_list.get_frame()
        # print(image)
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + image + b'\r\n')

@app.route('/broadcast')
def broadcast():
    if image_list.empty() == False:
        return Response(gen(image_list),
                        mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    app.run(host='0.0.0.0',  # 任何ip都可以访问
            port=9090,  # 端口
            debug=True
            )
