package com.pratheeks.AuthZee.dao;

import com.pratheeks.AuthZee.model.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO: !!! Not implemented properly !!!
 */

public class UserDao implements Dao<User>{

    private static final ArrayList<User> users = new ArrayList<>(Arrays.asList(
            new User(1, 10),
            new User(2, 20),
            new User(100, 100))
    );

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
                return users.get(0);
            case 2:
                return users.get(1);
            case 100:
                return users.get(2);
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
                return users.get(0);
            case "user2":
                return users.get(1);
            case "user100":
                return users.get(2);
            default:
                return null;
        }
    }

    @Override
    public ArrayList<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
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
