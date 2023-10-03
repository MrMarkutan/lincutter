package userway.lincutter.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlGenerator {

    public static String generateShortUrl(String originalUrl) {
        final int NUM_CHARS_SHORT_LINK = 7;
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(originalUrl.getBytes());
            byte[] messageDigest = digest.digest();            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.substring(0, NUM_CHARS_SHORT_LINK);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);

        }
    }

//    public static String generateRandomShortUrl(String longURL) {
//        final int NUM_CHARS_SHORT_LINK = 7;
//        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//
//        char[] result = new char[NUM_CHARS_SHORT_LINK];
//        for (int i = 0; i < NUM_CHARS_SHORT_LINK; i++) {
//            int randomIndex = random.nextInt(ALPHABET.length() - 1);
//            result[i] = ALPHABET.charAt(randomIndex);
//        }
//        return new String(result);
//    }
}
