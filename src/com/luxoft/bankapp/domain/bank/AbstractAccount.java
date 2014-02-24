package com.luxoft.bankapp.domain.bank;

//3d exercise
public abstract class AbstractAccount implements Account {
    protected Integer id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
