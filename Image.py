import queue
import numpy as np
import os
import base64
import cv2


class Image(object):
    def __init__(self):
        self.q = queue.Queue()

    def push(self, imgdata):
        self.q.put(imgdata)

    def empty(self):
        if not self.q.empty:
            return True
        else:
            return False

    def size(self):
        return self.q.qsize()

    def __del__(self):
        pass

    def base64_cv2(base64_str):
        imgString = base64.b64decode(base64_str)
        nparr = np.fromstring(imgString, np.uint8)
        image = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        return image

    def get_frame(self):
        img = ''
        if not self.q.empty():
            img = self.q.get()[1]
        else:
            return ''
        base64_cv2(img)
        ret, jpeg = cv2.imencode('.jpg', img)
        return jpeg.tobytes()
