package cryptotest;

public class Main {
    public static void main(String[] args) throws Exception {
        RSAExample rsaExample = new RSAExample();

        rsaExample.saveToFile("", "");

        String originalMessage = "Hello, RSA!";
        System.out.println("Mensagem original: " + originalMessage);

        byte[] encryptedMessage = rsaExample.encrypt(originalMessage);

        System.out.println("Mensagem encriptada: " + new String(encryptedMessage));

        String decryptedMessage = rsaExample.decrypt(encryptedMessage);
        System.out.println("Mensagem desencriptada: " + decryptedMessage);
    }
}
