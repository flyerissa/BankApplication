package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.socket_multithread.BankClientMock;

/**
 * Created by User on 06.03.14.
 */
public class BankServerThreadedTest {
    public static void main(String[] args) {
        try {
            Bank bank = BankService.getInstance().findBankByName("Bank");
            Client client1 = BankService.getInstance().findClientByNameAsActive(bank, "JJ KK");
            double balance = client1.getBalance();
            BankClientMock clientMock = null;
            for (int i = 0; i < 5; i++) {
                clientMock = new BankClientMock(client1);
                clientMock.start();
            }

            for (int i = 0; i < 5; i++) {
                clientMock.join();
            }

            //double amount2 = client1.getBalance();
            //assertEquals(balance - 50, amount2);

        } catch (BankNotFoundException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
