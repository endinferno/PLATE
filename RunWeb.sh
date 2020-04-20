#!/bin/bash

# gunicorn -w 4 -b 127.0.0.1:9090 app:app
flask run -h 0.0.0.0 -p 9090
