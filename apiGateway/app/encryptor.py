import os
import base64

from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives.kdf.scrypt import Scrypt

class Encryptor:


  # Function that encrypts a secret.
  # Input: secret and key.
  # Output: nonce, key and ciphertext.
  def encryptMsg(self, secret, key) :

       secret = secret.encode()

       nonce = os.urandom(12)   # criar mecanismo de controlo de nonces

       aesgcm = AESGCM(key)

       ct = aesgcm.encrypt(nonce, secret, None)

       base64EncodedStr = base64.b64encode(nonce + key + ct)

       return base64EncodedStr

    
  # Function that desencrypts a ciphertext.
  # Input: secret and key.
  # Output: nonce, key and ciphertext.
  def desencryptMsg(self, ct) :

        ct = base64.b64decode(ct)

        nonce = ct[:12]
        key =  ct[12:44]
        auxCt= ct[44:]

        aesgcm = AESGCM(key)

        original_data = aesgcm.decrypt(nonce, auxCt, None)
        return original_data.decode()


  # Function that reads the password file.
  # Output: key.
  def readPassword(self):

        with open('password.txt', 'rb') as f:
             password = f.read()

        return password
        

  # Function that generates a key from a password.
  # Output: key.
  def secretGenerator(self) :

        password = self.readPassword()

        backend = default_backend()

        # Salts should be randomly generated
        salt = os.urandom(16)

        kdf = Scrypt(
            salt=salt,
            length=32,
            n=2**14,
            r=8,
            p=1,
            backend=backend
        )


        key = kdf.derive(password)


        # verify
        kdf = Scrypt(
            salt=salt,
            length=32,
            n=2**14,
            r=8,
            p=1,
            backend=backend
        )

        kdf.verify(password, key)

        # return the passphrase
        return key
