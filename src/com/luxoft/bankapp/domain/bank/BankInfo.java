package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.annotations.NoDB;

import java.util.List;
import java.util.Map;

/**
 * Created by aili on 24.02.14.
 */
public class BankInfo {
    /**
     * Total number of clients of the bank
     */
    @NoDB
    int numberOfClients;
    /**
     * The sum of all accounts of all clients
     */
    @NoDB
    Double totalAccountSum;
    /**
     * List of clients by the city
     */
    @NoDB
    Map<String, List<Client>> clientsByCity;

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public Double getTotalAccountSum() {
        return totalAccountSum;
    }

    public void setTotalAccountSum(Double totalAccountSum) {
        this.totalAccountSum = totalAccountSum;
    }

    public Map<String, List<Client>> getClientsByCity() {
        return clientsByCity;
    }

    public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
        this.clientsByCity = clientsByCity;
    }


}
