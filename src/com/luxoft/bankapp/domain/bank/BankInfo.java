package com.luxoft.bankapp.domain.bank;

import java.util.List;
import java.util.Map;

/**
 * Created by aili on 24.02.14.
 */
public class BankInfo {
    /**
     * Total number of clients of the bank
     */
    int numberOfClients;
    /**
     * The sum of all accounts of all clients
     */
    Double totalAccountSum;
    /**
     * List of clients by the city
     */
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
