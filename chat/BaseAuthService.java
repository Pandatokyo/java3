package src.chat;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Objects;

import static src.lesson2.JdbcApp.connect;
import static src.lesson2.JdbcApp.disconnect;


public class BaseAuthService implements Auth {
    private final List<User> users = new ArrayList<>();
    private static Connection connection;
    private static Statement stmt;

    public BaseAuthService() {
        users.add(new User("Alex", "password", "Alexander"));
        users.add(new User("Nick", "password1", "Nick"));
    }


    public String authByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (login.equals(user.login())
                    && password.equals(user.password()))
                return user.nickname();
        }
        return null;
    }

    // Домашнее задание

    public String authWithSql(String id, String password) {
        ResultSet rs = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement("SELECT password, name FROM students WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            String dbpass = null;
            String name = null;
            while (rs.next()) {
                dbpass = rs.getString(1);
                name = rs.getString(2);
            }
            if (Objects.equals(dbpass, password)) {
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }
}
