package cryptotest;

import java.security.*;
import java.util.Base64;

public class RSASHA256Example {

    private KeyPair keyPair;

    public RSASHA256Example() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    public byte[] sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(message.getBytes());
        return signature.sign();
    }

    public boolean verify(String message, byte[] signatureBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(keyPair.getPublic());
        signature.update(message.getBytes());
        return signature.verify(signatureBytes);
    }

    public static void main(String[] args) {
        try {
            RSASHA256Example example = new RSASHA256Example();

            String message = "Hello, World!";
            byte[] signatureBytes = example.sign(message);
            String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
            System.out.println("Assinatura: " + signatureBase64);

            boolean isVerified = example.verify(message, signatureBytes);
            System.out.println("Verificação: " + isVerified);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
