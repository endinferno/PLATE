import queue
import numpy as np
import os, base64
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

    def get_frame(self):
        img = ''
        if not self.q.empty():
            img = self.q.get()[1]
        else:
            return ''
        # imgdata = base64.b64decode(img)
        # img_array = np.fromstring(imgdata,np.uint8)
        # img = cv2.imdecode(img_array,cv2.COLOR_BGR2RGB)
        # ret, jpeg = cv2.imencode('.jpg', imgdata)
        return img
