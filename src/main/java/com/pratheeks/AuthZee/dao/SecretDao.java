package com.pratheeks.AuthZee.dao;

/**
 * TODO: !!! Not implemented properly !!!
 */

public class SecretDao {

    private static String authSecret = "AUTH-SECRET";

    /**
     * Retrieve the authSecret from DB and return it
     * @return authSecret
     */
    public static String getAuthSecret(){
        return authSecret;
    }

    /**
     * Save the new authSecret in DB
     * @param authSecret New authSecret
     */
    public static void setAuthSecret(String authSecret){
        //TODO: call db to store authSecret
        SecretDao.authSecret = authSecret;
    }
}
