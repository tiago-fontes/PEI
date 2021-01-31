from encryptor import Encryptor
import getpass


class ciphertextGenerator():

	 def generateCiphertext(self):

			encryptor = Encryptor()
			key = encryptor.secretGenerator()
	 
			carId = getpass.getpass('CarID: ')
			deviceID = getpass.getpass('DeviceID: ')
	
			carIdEncrypted = encryptor.encryptMsg(carId, key)
			deviceIDEncrypted  = encryptor.encryptMsg(deviceID, key)

			return (carIdEncrypted, deviceIDEncrypted)



def main():

			generator = ciphertextGenerator()

			(matriculaEncrypted, deviceIDEncrypted) = generator.generateCiphertext()

			print("\n")
			print("CarID:")
			print(matriculaEncrypted)
			print("\n")
			print("DeviceID:")
			print(deviceIDEncrypted)

main()
