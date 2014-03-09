package com.luxoft.bankapp.DAO;

import java.sql.Connection;
import java.util.concurrent.Callable;

/**
 * Created by aili on 09.03.14.
 */
public class TransactionManager {

    private DataSource dataSource;

    private static final TransactionManager INSTANCE = new TransactionManager(DataSource.getInstance());

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TransactionManager getInstance() {
        return INSTANCE; // TODO: avoid singletones
    }

    public <V> V doInTransaction(Callable<V> callable) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        try {
            V value = callable.call();
            connection.commit();
            return value;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            dataSource.closeConnection();//TODO: release connection or reuse it??
        }
    }
}
