package com.pratheeks.AuthZee.dao;

import com.pratheeks.AuthZee.model.User;

import java.util.ArrayList;

/**
 * TODO: !!! Not implemented properly !!!
 */

public class UserDao implements Dao<User>{

    /**
     * SELECT user having the id from DB and return user object
     * @param id UserID of the user
     * @return user - User object corresponding to the username
     *         or null
     */
    @Override
    public User get(long id) {
        
        switch ((int)id){
            case 1:
                return new User(1, 10);
            case 2:
                return new User(2, 20);
            case 100:
                return new User(100, 100);
            default:
                return null;
        }
    }

    /**
     * SELECT user having the username from DB and return user object
     * @param username Username of the user
     * @return user - User object corresponding to the username
     *         or null
     */
    @Override
    public User get(String username) {
        if (username==null) return null;

        switch (username){
            case "user1":
                return new User(1, 10);
            case "user2":
                return new User(2, 20);
            case "admin":
                return new User(100, 100);
            default:
                return null;
        }
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public long save(User user) {
        return 0;
    }

    @Override
    public void save(ArrayList<User> list) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
