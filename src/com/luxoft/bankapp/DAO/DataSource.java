package com.luxoft.bankapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by aili on 02.03.14.
 */
public class DataSource {

    private final ThreadLocal<Connection> connections = new ThreadLocal<>();

    private static final DataSource INSTANCE = new DataSource();

    public static DataSource getInstance() {
        return INSTANCE; //TODO: dont use singletones
    }

    public Connection getConnection() throws SQLException {
        Connection connection = connections.get();
        if (connection != null && connection.isClosed()) {
            connection = null;
        }
        if (connection == null) {
            connection = openConnection();
        }
        connections.set(connection);
        return connection;
    }

    public Connection openConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/bankapp",
                    "sa", // login
                    "" // password
            );
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void closeConnection() throws SQLException {
        Connection connection = connections.get();
        if (connection != null) {
            connections.remove();
            connection.close();
        }
    }
}