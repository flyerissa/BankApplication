package com.luxoft.bankapp.DAO;

/**
 * Created by aili on 09.03.14.
 */
public abstract class BaseDAO {

    private DataSource dataSource;

    public BaseDAO() {
        dataSource = DataSource.getInstance();//TODO use dependency injection
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
