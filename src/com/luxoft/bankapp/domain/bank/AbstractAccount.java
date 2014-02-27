package com.luxoft.bankapp.domain.bank;

import java.util.Map;

//3d exercise
public abstract class AbstractAccount implements Account {
    protected Integer id;
    protected String type;
    protected Double balance;
    protected Double overdraft;

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


}
