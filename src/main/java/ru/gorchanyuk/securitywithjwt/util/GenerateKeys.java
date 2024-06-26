package ru.gorchanyuk.securitywithjwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

/**
 * Класс для генерации ключей
 */
public class GenerateKeys {

    public static void main(String[] args) {
        System.out.println(generateKey());
        System.out.println(generateKey());
    }

    private static String generateKey() {
        return Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
    }

}