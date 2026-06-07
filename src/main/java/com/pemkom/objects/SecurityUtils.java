package com.pemkom.objects;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SecurityUtils {

    // Type-Safety Enforcement - mencegah typo nama algoritma
    public static final String ALGO_SHA1 = "SHA-1";
    public static final String ALGO_SHA256 = "SHA-256";
    public static final String ALGO_SHA512 = "SHA-512";

    // Reusable static method - bisa dipakai tanpa instansiasi objek
    public static String hashSHA256(String input) {
        try {
            // Tahap 3: Mesin Kriptografi JCA
            MessageDigest md = MessageDigest.getInstance(ALGO_SHA256);

            // Tahap 1 & 2: Teks Mentah -> Byte Array (UTF-8 untuk konsistensi)
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Tahap 5: Merakit String Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Tahap 4: Bitwise Masking 0xff - mencegah Sign Extension Error
                String hex = String.format("%02x", 0xff & b);
                hexString.append(hex);
            }

            return hexString.toString(); // 64 karakter hex

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method umum untuk fleksibilitas algoritma
    public static String getHash(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", 0xff & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
