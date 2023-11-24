package edu.school21.chat.models;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

import javax.sql.DataSource;

public class DataBaseConnection {
    private static final String CONNECTION = DataBaseConnectionVal.getAnyVal("db.connection");
    private static final String LOGIN = DataBaseConnectionVal.getAnyVal("db.login");
    private static final String PASSWORD = DataBaseConnectionVal.getAnyVal("db.pass");

    public static String getConnection() {
        return CONNECTION;
    }

    public static String getLogin() {
        return LOGIN;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public DataBaseConnection() {
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(CONNECTION, LOGIN, PASSWORD)) {
            PreparedStatement userStatement = connection
                    .prepareStatement("SELECT userid, login, password, chatroomname FROM Chat.User " +
                            "JOIN chat.chatroom ON chatroomowner = Chat.user.userid");

            ResultSet resultSet = userStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("userid") + " " + resultSet.getString("login") + " "
                        + resultSet.getString("chatroomname"));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

    public static DataSource connectToDb() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(CONNECTION);
        config.setUsername(LOGIN);
        config.setPassword(PASSWORD);
        return new HikariDataSource(config);
    }
}
