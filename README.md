# Projeto JWT & RSA Exemplo

Este projeto fornece exemplos de implementações em Java para trabalhar com JWT (JSON Web Tokens) e chaves RSA.

## Sumário

- [Introdução](#introdução)
- [Requisitos](#requisitos)
- [Classe RSAExample](#classe-rsaexample)
- [Classe JWTSigner](#classe-jwtsigner)
- [Uso](#uso)

## Introdução

O JWT é um padrão (RFC 7519) que define como transmitir informações de forma segura entre partes usando tokens JSON. Este projeto contém uma implementação que ilustra como assinar um JWT usando o algoritmo RS256.

## Requisitos

- Java 8+
- Dependências Maven (caso utilize bibliotecas externas)

## Classe RSAExample

A classe `RSAExample` ilustra como gerar um par de chaves RSA (chave pública e chave privada) e como salvar essas chaves em arquivos `.pem`.

Principais métodos:

- `generateKeyPair()`: Gera um par de chaves RSA.
- `saveToFile()`: Salva as chaves em arquivos `.pem`.

## Classe JWTSigner

A classe `JWTSigner` é responsável por assinar um payload JWT usando a chave privada RSA.

Principais métodos:

- `signJWT(String payload)`: Recebe um payload em formato de string JSON, assina e retorna o JWT completo.

## Uso

```java
// Gerando chaves RSA e salvando em arquivos
RSAExample rsaExample = new RSAExample();
rsaExample.saveToFile("path/to/private_key.pem", "path/to/public_key.pem");

// Assinando um JWT
JWTSigner signer = new JWTSigner();
String payload = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
String jwt = signer.signJWT(payload);
System.out.println("JWT Assinado: " + jwt);
```
