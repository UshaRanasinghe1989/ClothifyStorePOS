package edu.icet.pos.util;

import java.security.SecureRandom;

public class OTPGenerator extends EmailUtil {
    public static String generateSecretKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];

        secureRandom.nextBytes(bytes);
        StringBuilder secretKey = new StringBuilder();

        for (byte b : bytes) {
            secretKey.append(String.format("%02x", b));
        }

        return secretKey.toString();
    }
    public static String generateOTP(String secretKey,int length){
        String allowedCharacters = "0123456789";
        StringBuilder otp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(allowedCharacters.length());
            otp.append(allowedCharacters.charAt(randomIndex));
        }

        return otp.toString();
    }
}
