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
        try(PreparedStatement st = connection.prepareStatement("INSERT INTO users (email, password) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, object.getMail());
            st.setString(2, object.getPassword());
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()) {
                if(rs.next()) {
                    object.setId(rs.getLong(1));
                }
            }
        }
        return object;
    }

    @Override
    public void remove(User object) throws SQLException {
        try(PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            st.setLong(1, object.getId());
            st.executeUpdate();
        }
    }

    @Override
    public User merge(User object) throws SQLException {
        try(PreparedStatement st = connection.prepareStatement("UPDATE users SET email = ?, password = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, object.getMail());
            st.setString(2, object.getPassword());
            st.setLong(3, object.getId());
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()) {
                if(rs.next()) {
                    object.setId(rs.getLong(1));
                }
            }
            return object;
        }
    }

    @Override
    public User find(long id) throws SQLException {
        try(PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            st.setLong(1, id);
            try(ResultSet resultSet = st.executeQuery()) {
                if(resultSet.next()) {
                    User user = new User(resultSet.getString(2), resultSet.getString(3));
                    user.setId(resultSet.getLong(1));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User find(String name) throws SQLException {
        try(PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            st.setString(1, name);
            try(ResultSet resultSet = st.executeQuery()) {
                if(resultSet.next()) {
                    User user = new User(resultSet.getString(2), resultSet.getString(3));
                    user.setId(resultSet.getLong(1));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try(PreparedStatement st = connection.prepareStatement("SELECT * FROM users")) {
            try(ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(resultSet.getString(2), resultSet.getString(3));
                    user.setId(resultSet.getLong(1));
                    users.add(user);
                }
            }
        }
        return users;
    }
}
