package edu.icet.pos.db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothify_pos", "root", "root");
    }
    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection!=null?dbConnection:(dbConnection=new DBConnection());
    }

    public Connection getConnection(){
        return connection;
    }
}
