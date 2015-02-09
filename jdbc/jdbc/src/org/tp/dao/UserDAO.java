package org.tp.dao;

import org.tp.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pitton on 2015-02-09.
 */
public class UserDAO implements DAO<User> {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User add(User object) throws SQLException {
        return null;
    }

    @Override
    public void remove(User object) throws SQLException {

    }

    @Override
    public User merge(User object) throws SQLException {
        return null;
    }

    @Override
    public User find(long id) throws SQLException {
        return null;
    }

    @Override
    public User find(String name) throws SQLException {
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }
}
