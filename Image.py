import queue, time, os, base64, cv2
import numpy as np
import PIL.Image
from io import BytesIO


class Image(object):
    def __init__(self):
        self.q = queue.Queue()
        self.current_img = b''

    def push(self, imgdata):
        if self.size() == 1000:
            self.q.get()
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

    def base64_cv2(self, base64_str):
        base64_str = base64_str.split(',')[-1]
        pl_img = PIL.Image.open(BytesIO(base64.b64decode(base64_str)))
        x, y = pl_img.size
        p = PIL.Image.new('RGBA', pl_img.size, (255,255,255))
        p.paste(pl_img, (0,0,x,y), pl_img)
        cv_img = cv2.cvtColor(np.asarray(p), cv2.COLOR_RGB2BGR)
        return cv_img

    def get_frame(self):
        # time.sleep(0.1)
        img = ''
        if not self.q.empty():
            img = self.base64_cv2(self.q.get()[1])
        else:
            if self.current_img == b'':
                return self.current_img
            else:
                return self.current_img.tobytes()
        ret, jpeg = cv2.imencode('.jpg', img)
        self.current_img = jpeg
        return jpeg.tobytes()
