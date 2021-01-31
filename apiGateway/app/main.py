#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
WSGI entry point - Starts Flask App locally in debugg mode
"""

from app import app

# For debugging; will not run if launched from Nginx
if __name__ == "__main__":
    #threaded=True
    app.run(port=app.config['PORT'], debug=True, host=app.config['HOST'])
