package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createScheme = "CREATE SCHEMA IF NOT EXISTS new_schema";
        String createTableCommand =
                "CREATE TABLE IF NOT EXISTS new_schema.users " +
                        "(id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(20) NOT NULL, " +
                        "lastname VARCHAR(20) NOT NULL, " +
                        "age INT NOT NULL, " +
                        "PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createScheme);
            statement.executeUpdate(createTableCommand);
        } catch (Exception ex) {
            System.out.println("Throwing exception in 'createUsersTable' method");
        }
    }

    public void dropUsersTable() {
        String dropTableCommand = "DROP TABLE IF EXISTS new_schema.users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableCommand);
        } catch (Exception ex) {
            System.out.println("Throwing exception in 'dropUsersTable' method");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserCommand = "INSERT INTO new_schema.users (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            System.out.println("Throwing exception in 'saveUser' method");
        }
    }

    public void removeUserById(long id) {
        String removeByIdCommand = "DELETE FROM new_schema.users WHERE (id = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeByIdCommand)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Throwing exception in 'removeUserById' method");
        }
    }

    public List<User> getAllUsers() {
        String getUsersCommand = "SELECT * FROM new_schema.users";
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getUsersCommand);
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastname"), resultSet.getByte("age")));
            }
        } catch (Exception e) {
            System.out.println("Throwing exception in 'getAllUsers' method");
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanTableCommand = "TRUNCATE new_schema.users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanTableCommand);
        } catch (Exception ex) {
            System.out.println("Throwing exception in 'cleanUsersTable' method");
        }
    }
}
