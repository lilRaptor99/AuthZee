package com.pratheeks.AuthZee.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;

import static com.pratheeks.AuthZee.auth.Hasher.base64Encode;
import static org.junit.jupiter.api.Assertions.*;

class AuthorizationTest {

    // Validate token cookie tests
    @Test
    @DisplayName("Should return true for a valid user token cookie")
    void shouldReturnTrueForAValidUserTokenCookie() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);
        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertTrue(Authorization.isValidTokenCookie(authTokenCookie));
    }

    @Test
    @DisplayName("Should return false for incomplete token")
    void shouldReturnFalseForIncompleteToken() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);

        String[] propArray = new String[]{"userID", "userLevel", "expiration", "hash"};

        for(String prop : propArray){
            JSONObject testToken = new JSONObject(jsonUserToken);
            testToken.remove(prop);
            Cookie authTokenCookie = new Cookie("userToken", base64Encode(testToken.toString()));
            assertFalse(Authorization.isValidTokenCookie(authTokenCookie));
        }
    }

    @Test
    @DisplayName("Should return false for expired token")
    void shouldReturnFalseForExpiredToken() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);

        jsonUserToken.put("expiration", LocalDateTime.now().minusDays(1));

        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertFalse(Authorization.isValidTokenCookie(authTokenCookie));
    }

    @Test
    @DisplayName("Should return false for invalid token hash")
    void shouldReturnFalseForInvalidTokenHash() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);

        jsonUserToken.put("hash", "sdflsjjshdfasjdhfalsdjhfaskjh");

        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertFalse(Authorization.isValidTokenCookie(authTokenCookie));
    }

    
    // API endpoint authorization tests
    @Test
    @DisplayName("Should return true for valid token cookie and access level")
    void shouldReturnTrueForValidTokenCookieAndAccessLevel() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);
        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertTrue(Authorization.isAuthorized(authTokenCookie, 10));
    }

    @Test
    @DisplayName("Should return true for public endpoints")
    void shouldReturnTrueForPublicEndpoints() {
        assertTrue(Authorization.isAuthorized(null, 0));
    }

    @Test
    @DisplayName("Should return false for invalid token cookie")
    void shouldReturnFalseForInvalidTokenCookie() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);

        jsonUserToken.put("hash", "sdflsjjshdfasjdhfalsdjhfaskjh");

        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertFalse(Authorization.isAuthorized(authTokenCookie, 10));
    }

    @Test
    @DisplayName("Should return false for lower access level")
    void shouldReturnFalseForLowerAccessLevel() {
        JSONObject jsonUserToken = Authentication.getUserToken("user1", "user1", LocalDateTime.now().plusDays(1));
        assertNotNull(jsonUserToken);
        Cookie authTokenCookie = new Cookie("userToken", base64Encode(jsonUserToken.toString()));

        assertFalse(Authorization.isAuthorized(authTokenCookie, 20));
    }
}