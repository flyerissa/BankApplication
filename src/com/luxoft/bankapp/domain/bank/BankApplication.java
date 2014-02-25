package com.luxoft.bankapp.domain.bank;

import java.util.ArrayList;

// 3d exercise
public class BankApplication {


    private static ArrayList<Bank> listOfBanks = new ArrayList<Bank>();

    public static ArrayList<Bank> getListOfBanks() {
        return listOfBanks;
    }

    public static void addBank(Bank bank) {
        listOfBanks.add(bank);
    }

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.setName("Bank");
        listOfBanks.add(bank);
        //Client cl1 = new Client("JJ KK", Gender.MALE);
        //Client cl2 = new Client("JJ RR", Gender.FEMALE);
        //cl1.setCity("Kyiv");
        // cl2.setCity("London");
       /* try {
            cl1.addAccount("C", 2000, 200);
            cl2.addAccount("S", 1000, 0);
            bank.addClient(cl1);
            bank.addClient(cl2);
        } catch (IllegalArgumentException e) {
            System.out.println("Balance and overdraft should be greater or equal zero");
        } catch (ClientExistsException e) {
            System.out.println("Client with the same name already exists!");
        }

        modifyBank(bank);

        BankReport.getNumberOfClients(bank);
        BankReport.getAccountsNumber(bank);
        BankReport.getBankCreditSum(bank);
        BankReport.getClientsByCity(bank);
        BankReport.getClientsSorted(bank);

    }
*/
    /*private static void modifyBank(Bank bank) {
        Client client3 = new Client("OOO", Gender.FEMALE);
        try {
            client3.addAccount("C", 5000, 1000);
            Account account = client3.getActiveAccount();
            account.deposit(1000);
            account.withdraw(6500);
            bank.addClient(client3);
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

    /*private static void printBalance(Bank bank) {
        for (Client c : bank.getClients()) {
            System.out.println("Balance is: " + c.getActiveAccount().getBalance());
        }
    }
*/
    }
}

