package com.pratheeks.AuthZee.auth;

import com.pratheeks.AuthZee.dao.UserDao;
import com.pratheeks.AuthZee.model.User;
import org.json.JSONObject;

import java.time.LocalDateTime;


public class Authentication {

    /**
     * Authenticate user and return UserToken as JSONObject
     * @param username Entered username
     * @param password Entered password
     * @return userToken - JSON userToken with userID, userLevel, and authHash
     *                      or null - Invalid user credentials
     */
    public static JSONObject getUserToken(String username, String password, LocalDateTime expiryDate){
        User user = validateUserCredentials(username, password);

        // Invalid user credentials
        if(user == null) return null;

        String userID = Long.toString(user.getId());
        String userLevel = Long.toString(user.getLevel());
        String expDate = expiryDate.toString();
        String tokenHash = Hasher.getTokenHash(userID, userLevel, expDate);

        JSONObject tokenObj = new JSONObject();
        tokenObj.put("userID", userID);
        tokenObj.put("userLevel", userLevel);
        tokenObj.put("expiration", expDate);
        tokenObj.put("hash", tokenHash);

        System.out.println("Created AuthToken = " + tokenObj.toString());

        return tokenObj;
    }


    /**
     * Validate user credentials and return userID and the userLevel if the user credentials are valid
     * @param username Entered username
     * @param password Entered password
     * @return user - user object related to username after validating credentials
     *         null - if user credentials are invalid
     */
    private static User validateUserCredentials(String username, String password){
        UserDao userDao = new UserDao();

        User user = userDao.get(username);
        //Invalid username
        if(user == null) return null;

        String storedUserPassword = user.getPassword();

        //Invalid password
        if(!Hasher.hashPassword(password).equals(storedUserPassword)) return null;

        return user;
    }



}
