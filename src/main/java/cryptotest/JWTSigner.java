package cryptotest;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;


public class JWTSigner {

    private KeyPair keyPair;

    public JWTSigner() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    private String base64UrlEncode(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    }

    public String getPrivateKeyInPKCS8() {
        byte[] pkcs8EncodedKey = keyPair.getPrivate().getEncoded();
        return Base64.getEncoder().encodeToString(pkcs8EncodedKey);
    }

    public String signJWT(String payload) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String header = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";

        String base64UrlHeader = base64UrlEncode(header.getBytes());
        String base64UrlPayload = base64UrlEncode(payload.getBytes());

        String toSign = base64UrlHeader + "." + base64UrlPayload;

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(toSign.getBytes());
        byte[] signatureBytes = signature.sign();

        String base64UrlSignature = base64UrlEncode(signatureBytes);
        return toSign + "." + base64UrlSignature;
    }

    public String getPublicKeyInPKCS1() {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

        try {
            // ASN.1 structure for PKCS#1 RSA public key
            ASN1EncodableVector vector = new ASN1EncodableVector();
            vector.add(new ASN1Integer(rsaPublicKey.getModulus()));
            vector.add(new ASN1Integer(rsaPublicKey.getPublicExponent()));

            byte[] publicKeyPKCS1 = new DERSequence(vector).getEncoded();

            return "-----BEGIN RSA PUBLIC KEY-----\n" +
                    Base64.getMimeEncoder().encodeToString(publicKeyPKCS1) +
                    "\n-----END RSA PUBLIC KEY-----";
        } catch (IOException e) {
            throw new RuntimeException("Error encoding public key", e);
        }
    }

    public static void main(String[] args) {
        try {
            JWTSigner signer = new JWTSigner();

            String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
            String jwt = signer.signJWT(payload);

            System.out.println("JWT Assinado: " + jwt);
            System.out.println("Chave pública PKCS#1:\n" + signer.getPublicKeyInPKCS1());
            System.out.println("Chave privada PKCS#8: " + signer.getPrivateKeyInPKCS8());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
