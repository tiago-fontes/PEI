import socket
import pandas as pd
from threading import Timer

NR_CONN = 2
PORT = 6666


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((socket.gethostname(),PORT))
s.listen(NR_CONN)

def sendRow():
    dfExemplo = pd.DataFrame(columns=list('ABC'))
    dfExemplo.loc[0] = [0,1,'Hello']
    string = dfExemplo.to_string()
    clt.send(bytes(string,"utf-8"))
    Timer(3,sendRow).start()	





while True:
	clt,adr = s.accept()
	print("Connection established with " , adr)
	sendRow()
