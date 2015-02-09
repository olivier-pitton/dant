package org.tp.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tp.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pitton on 2015-02-09.
 */
public class UserDAOTest {

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        createDatabase(connection);
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    private void createDatabase(Connection connection) throws SQLException {
        try(Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, email VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL)");
        }
    }

    @Test
    public void testAdd() throws SQLException {
        User user = new User("olivier", "mdp");
        UserDAO userDAO = new UserDAO(connection);
        User result = userDAO.add(user);
        assertEquals(user.getMail(), result.getMail());
        assertEquals(user.getPassword(), result.getPassword());
        assertTrue("Id is " + user.getId(), user.getId() > 0L);
        userDAO.remove(result);
    }

    @Test
    public void testRemove() throws SQLException {
        User user = new User("olivier", "mdp");
        UserDAO userDAO = new UserDAO(connection);
        User result = userDAO.add(user);
        userDAO.remove(result);

        assertNull(userDAO.find(user.getId()));
    }


    @Test
    public void testMerge() throws SQLException {
        User user = new User("olivier", "mdp");
        UserDAO userDAO = new UserDAO(connection);
        User result = userDAO.add(user);

        result.setMail("nouvelemail");
        user = userDAO.merge(result);
        assertEquals(user.getId(), result.getId());

        userDAO.remove(user);
    }

    @Test
    public void testFindName() throws SQLException {
        User user = new User("testFindName", "mdp");
        UserDAO userDAO = new UserDAO(connection);
        User result = userDAO.add(user);

        assertEquals(result, userDAO.find("testFindName"));
    }

    @Test
    public void testFindAll() throws SQLException {
        UserDAO userDAO = new UserDAO(connection);
        for(int i = 0 ; i < 5 ; i++) {
            userDAO.add(new User(Integer.toString(i), "test"));
        }
        List<User> all = userDAO.findAll();
        assertEquals(all.size(), 5);
        Collections.sort(all);
        for(int i = 0 ; i < 5 ; i++) {
            assertEquals(all.get(i).getMail(), Integer.toString(i));
            userDAO.remove(all.get(i));
        }
    }

}
