package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.annotations.NoDB;

import java.io.Serializable;
import java.util.Map;

//3d exercise
public abstract class AbstractAccount implements Account, Serializable {
    @NoDB
    protected Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractAccount)) return false;

        AbstractAccount that = (AbstractAccount) o;

        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @NoDB

    protected String type;
    protected Double balance;
    protected Double overdraft;
    @NoDB
    protected Integer client_id;

    @Override
    public void setClientID(Integer id) {
        this.client_id = id;
    }

    @Override
    public Integer getClientID() {
        return client_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Double overdraft) {
        this.overdraft = overdraft;
    }

    public String getAccountType() {
        return type;
    }

    public void setAccountType(String type) {
        this.type = type;
    }


    public void parseFeed(Map<String, String> feed) {
        String accountType = feed.get("accounttype");
        Double balance = Double.parseDouble(feed.get("balance"));
        this.setAccountType(accountType);
        this.setBalance(balance);

    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
