package org.tp.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pitton on 2015-02-09.
 */
public interface DAO<T> {

    /**
     * Adds a new user and returns him with his new generated id.
     * @param object an user
     * @return a synchronized user with db
     * @throws SQLException
     */
    T add(T object) throws SQLException;

    /**
     * Remove the specified user from the DB
     * @param object an user
     * @throws SQLException
     */
    void remove(T object)  throws SQLException;

    /**
     * Update the specified user. Returns the synchronized user with db
     * @param object an user
     * @return a synchronized user with db
     * @throws SQLException
     */
    T merge(T object) throws SQLException;

    /**
     * Retrieves an user based on his id
     * @param id an user id
     * @return a user
     * @throws SQLException
     */
    T find(long id) throws SQLException;

    /**
     * Retrieves an user based on his email
     * @param name an email
     * @return a user
     * @throws SQLException
     */
    T find(String name) throws SQLException;

    /**
     * Retrieves all users from the database
     * @return list of all users
     * @throws SQLException
     */
    List<T> findAll() throws SQLException;

}
