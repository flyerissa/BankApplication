package com.luxoft.bankapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by aili on 02.03.14.
 */
public class DataSource {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection != null && connection.isClosed()) {
            connection = null;
        }
        if (connection == null) {
            connection = openConnection();
        }
        return connection;
    }

    public Connection openConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            connection = DriverManager.getConnection("jdbc:h2:~/bankapp",
                    "sa", // login
                    "" // password
            );
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
