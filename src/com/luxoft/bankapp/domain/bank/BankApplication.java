package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

// 3d exercise
public class BankApplication {

    public static void main(String[] args) throws ClientExistsException {

        Bank bank = new Bank();
        Client cl1 = new Client("JJ KK", Gender.MALE);
        Client cl2 = new Client("JJ KK", Gender.FEMALE);
        try {
            cl1.addAccount("C", 2000, 200);
            cl2.addAccount("S", 1000, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Balance and overdraft should be greater or equal zero");
        }


        try {
            BankService.addClient(bank, cl1);
            BankService.addClient(bank, cl2);
        } catch (ClientExistsException e) {
            System.out.println("Client with the same name already exists!");
        }


        modifyBank(bank);
        printBalance(bank);

    }

    private static void modifyBank(Bank bank) {
        Client client3 = new Client("OOO", Gender.FEMALE);
        try {
            client3.addAccount("C", 5000, 200);
        } catch (IllegalArgumentException e) {
            System.out.println("Balance and overdraft should be greater or equal zero");
        }
        Account account = client3.getActiveAccount();
        account.deposit(1000);
        try {
            account.withdraw(8000);
        } catch (OverdraftLimitExceededException e) {
            System.out.println("Cant withdraw. Balance is " + e.getBalance() + ". Maximum amount to withdraw is " + e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.println("Not enough money to withdraw!");
        }
        try {
            BankService.addClient(bank, client3);
        } catch (ClientExistsException e) {
            System.out.println("Client with the same name already exists!");
        }
    }

    private static void printBalance(Bank bank) {
        for (Client c : bank.getClients()) {
            System.out.println("Balance is: " + c.getActiveAccount().getBalance());
        }
    }

}
