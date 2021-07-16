package com.pratheeks.AuthZee.auth;

import com.pratheeks.AuthZee.secrets.SecretVault;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;


public class Hasher {
    private static String authSecret = null;

    public static void setAuthSecret(String authSecret) {
        Hasher.authSecret = authSecret;
    }

    /**
     *
     * @param userID UserID as a String
     * @param userLevel UserLevel as a String
     * @param expiryDate java.time.LocalDateTime object as a String
     * @return Hashed token data with the authSecret. This hash can be used in creation
     *         and in validation of userTokens.
     */
    public static String getTokenHash(String userID, String userLevel, String expiryDate){
        if(authSecret == null) authSecret = SecretVault.getAuthSecret();
        return DigestUtils.sha256Hex(userID + userLevel + expiryDate + authSecret);
    }

    /**
     * TODO: !!! Not implemented properly !!!
     * Hash the password to compare with the hashed password saved in DB
     * @param password Entered password
     * @return Hashed password
     */
    public static String hashPassword(String password){
        return password;
    }


    /**
     * Encode a string with base64 encoding scheme
     * @param value Input string to be encoded
     * @return Base64 encoding of the input string
     */
    public static String base64Encode(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    /**
     * Decode a base64 encoded string
     * @param encodedString Base64 encoded string
     * @return Decoded string
     */
    public static String base64Decode(String encodedString){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
}
