package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//连接数据库
public class Mysql {

    private static Connection connection;

    private Mysql(){}

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://127.0.0.1:3306/cheer-vote?characterEncoding=utf-8&autoReconnect=true";
            connection = DriverManager.getConnection(jdbc, "root", "");
        }
        return connection;
    }
}
