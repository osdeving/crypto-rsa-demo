package cryptotest;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAExample {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAExample() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Tamanho da chave: 2048 bits
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public byte[] encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public String decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(encrypted));
    }

    public void saveToFile(String privateKeyPath, String publicKeyPath) throws IOException {
        // Salvar a chave privada em formato PEM
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
                Base64.getMimeEncoder().encodeToString(getPrivateKey().getEncoded()) +
                "\n-----END PRIVATE KEY-----\n";
        Files.write(Path.of(privateKeyPath), privateKeyPem.getBytes());

        // Salvar a chave pública em formato PEM
        String publicKeyPem = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getMimeEncoder().encodeToString(getPublicKey().getEncoded()) +
                "\n-----END PUBLIC KEY-----\n";
        Files.write(Path.of(publicKeyPath), publicKeyPem.getBytes());
    }
}
