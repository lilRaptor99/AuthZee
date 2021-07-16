package com.pratheeks.AuthZee.secrets;

import com.pratheeks.AuthZee.auth.Hasher;
import com.pratheeks.AuthZee.dao.SecretDao;

public class SecretVault {

    public static String getAuthSecret(){
        return SecretDao.getAuthSecret();
    }

    public static void changeAuthSecret(String newAuthSecret){
        SecretDao.setAuthSecret(newAuthSecret);
        Hasher.setAuthSecret(newAuthSecret);
        //TODO: Log Auth secret changed
    }
}
