package main.java.server;

import java.sql.*;
import java.util.Objects;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static String authWithSql(String id, String password) {
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
}
