package com.pratheeks.AuthZee.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @Test
    @DisplayName("Should return a valid token for valid credentials")
    void shouldReturnAValidTokenForValidCredentials() {
        LocalDateTime exp = LocalDateTime.now().plusDays(1);
        JSONObject token = Authentication.getUserToken("user1", "user1", exp);
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.getString("userID"), "1");
        Assertions.assertEquals(token.getString("userLevel"), "10");
        Assertions.assertEquals(token.getString("expiration"), exp.toString());
        Assertions.assertNotNull(token.getString("hash"));

        String hash = Hasher.getTokenHash("1", "10", exp.toString());

        Assertions.assertEquals(token.getString("hash"), hash);
    }

    @Test
    @DisplayName("Should reject past dates as expiry date")
    void shouldRejectPastDatesAsExpiryDate() {
        LocalDateTime exp = LocalDateTime.now().minusDays(1);
        JSONObject token = Authentication.getUserToken("user1", "user1", exp);
        Assertions.assertNull(token);
    }

    @Test
    @DisplayName("Should return null for invalid user credentials")
    void shouldReturnNullForInvalidUserCredentials() {
        LocalDateTime exp = LocalDateTime.now().plusDays(1);
        JSONObject token = Authentication.getUserToken("usersadfasdf", "usersdfasdfas", exp);
        Assertions.assertNull(token);
    }
}