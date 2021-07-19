package com.pratheeks.AuthZee.auth;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;

import java.time.LocalDateTime;

import static com.pratheeks.AuthZee.auth.Hasher.base64Decode;

public class Authorization {

    public static boolean isAuthorized(Cookie userToken, long endpointAccessLevel){

        // Public endpoint
        if(endpointAccessLevel == 0) return true;

        // Validate token cookie
        if (! isValidTokenCookie(userToken)) return false;

        // Check if user has access to the endpoint
        try {
            JSONObject JSONUserToken = getJSONToken(userToken);
            return Long.parseLong(JSONUserToken.getString("userLevel")) >= endpointAccessLevel;

        } catch (Exception e){
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if the user token cookie is a genuine cookie.
     * Performed checks:1. A valid JSON string
     *                  2. Has all the token attributes
     *                  3. Not expired
     *                  4. Hash matches with the computed hash
     * @param userToken Cookie having the user token
     * @return Whether the token cookie is valid or not
     */
    public static boolean isValidTokenCookie(Cookie userToken){
        try {
            JSONObject JSONToken = getJSONToken(userToken);


            if(JSONToken.has("userID") &&
                    JSONToken.has("userLevel") &&
                    JSONToken.has("expiration") &&
                    JSONToken.has("hash")
            ){
                // check if token is not expired
                LocalDateTime tokenExpDate = LocalDateTime.parse((JSONToken.getString("expiration"))); //LocalDateTime localDateTime = LocalDateTime.parse("2018-09-16T08:00:00");
                if(! tokenExpDate.isAfter(LocalDateTime.now())) return false;

                // Verify the token hash
                String serverHash = Hasher.getTokenHash(JSONToken.getString("userID"), JSONToken.getString("userLevel"), JSONToken.getString("expiration"));
                return serverHash.equals(JSONToken.getString("hash"));
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Decode and return userToken as a JSONObject from the userToken cookie
     * @param userToken UserToken cookie
     * @return userToken as JSONObject
     */
    private static JSONObject getJSONToken(Cookie userToken) throws JSONException {
        String decodedToken = base64Decode(userToken.getValue());
        //System.out.println("Decoded token: " + decodedToken);

        return new JSONObject(decodedToken);
    }
}
