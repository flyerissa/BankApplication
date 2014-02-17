package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.ArrayList;

// 3d exercise
public class BankApplication {
    public static ArrayList<Bank> listOfBanks = new ArrayList<Bank>();

    public static void main(String[] args) {

        Bank bank = new Bank();
        listOfBanks.add(bank);
        Client cl1 = new Client("JJ KK", Gender.MALE);
        Client cl2 = new Client("JJ RR", Gender.FEMALE);
        try {
            cl1.addAccount("C", 2000, 200);
            cl2.addAccount("S", 1000, 0);
            BankService.addClient(bank, cl1);
            BankService.addClient(bank, cl2);
        } catch (IllegalArgumentException e) {
            System.out.println("Balance and overdraft should be greater or equal zero");
        } catch (ClientExistsException e) {
            System.out.println("Client with the same name already exists!");
        }

        modifyBank(bank);
        printBalance(bank);
        BankService.findClientByName(bank);

    }

    private static void modifyBank(Bank bank) {
        Client client3 = new Client("OOO", Gender.FEMALE);
        try {
            client3.addAccount("C", 5000, 200);
            Account account = client3.getActiveAccount();
            account.deposit(1000);
            account.withdraw(8000);
            BankService.addClient(bank, client3);
        } catch (IllegalArgumentException e) {
            System.out.println("Balance and overdraft should be greater or equal zero");
        } catch (ClientExistsException e) {
            System.out.println("Client with the same name already exists!");
        } catch (OverdraftLimitExceededException e) {
            System.out.println("Cant withdraw. Balance is " + e.getBalance() + ". Maximum amount to withdraw is " + e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.println("Not enough money to withdraw!");
        }

    }

    private static void printBalance(Bank bank) {
        for (Client c : bank.getClients()) {
            System.out.println("Balance is: " + c.getActiveAccount().getBalance());
        }
    }

}
