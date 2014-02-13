package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.domain.bank.Gender;
import com.luxoft.bankapp.service.bank.BankService;


public class BankApplication {


    public static void main(String[] args) {

        Bank bank = new Bank();
        Client cl1 = new Client("JJ KK", Gender.MALE);
        Client cl2 = new Client("KK LL", Gender.FEMALE);
        cl1.addAccount("C", 2000, 200);
        cl2.addAccount("S", 1000, 0);

        BankService.addClient(bank, cl1);
        BankService.addClient(bank, cl2);

        modifyBank(bank);
        printBalance(bank);

    }

    private static void modifyBank(Bank bank) {
        Client client3 = new Client("OOO", Gender.FEMALE);
        client3.addAccount("C", 5000, 800);
        Account account = client3.getActiveAccount();
        account.deposit(1000);
        account.withdraw(6500);
        BankService.addClient(bank, client3);
    }

    private static void printBalance(Bank bank) {
        for (Client c : bank.getClients()) {
            System.out.println("Balance is: " + c.getActiveAccount().getBalance());
        }
    }


}
