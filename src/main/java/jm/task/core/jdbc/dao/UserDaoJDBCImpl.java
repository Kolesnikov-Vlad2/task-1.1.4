package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS dbuser (
                id SERIAL PRIMARY KEY,
                name VARCHAR(20) NOT NULL,
                lastName VARCHAR(20) NOT NULL,
                age SMALLINT NOT NULL)""";

        try (Connection connection = Util.connect();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {

        try (Connection Connection = Util.connect();
             Statement statement = Connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS dbuser");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection Connection = Util.connect();
             PreparedStatement prestate = Connection.prepareStatement("INSERT INTO dbuser (name, lastName, age) VALUES (?, ?, ?)")) {

            prestate.setString(1, name);
            prestate.setString(2, lastName);
            prestate.setByte(3, age);

            prestate.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Connection Connection = Util.connect();
             PreparedStatement prestate = Connection.prepareStatement("DELETE FROM dbuser WHERE Id = ?")) {

            prestate.setLong(1, id);
            prestate.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Connection Connection = Util.connect();
             Statement statement = Connection.createStatement();
             ResultSet table = statement.executeQuery("SELECT * FROM dbuser")) {

            while (table.next()) {
                User user = new User(table.getString(2), table.getString(3), table.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {

        try (Connection Connection = Util.connect();
             Statement statement = Connection.createStatement()) {

            statement.execute("TRUNCATE dbuser");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}