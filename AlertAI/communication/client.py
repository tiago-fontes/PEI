import socket


PORT = 6666


s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect((socket.gethostname(),PORT))

while True:
	msg = s.recv(1024)
	print(msg.decode("utf-8"))