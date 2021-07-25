package com.pratheeks.AuthZee.dao;

import java.util.ArrayList;

public interface Dao<T> {

    T get(long id);

    /** Get object using a unique identifier */
    T get(String uuid);

    ArrayList<T> getAll();

    /** Save and return id */
    void save(T t);

    /** Batch save */
    void save(ArrayList<T> list);

    void update(T t);

    void delete(T t);
}
