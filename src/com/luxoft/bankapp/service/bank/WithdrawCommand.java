package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.ui.BankCommander;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        Client current = BankCommander.getActiveClient();
        Set<Account> accountSet = current.getAccounts();
        System.out.println(accountSet);
        System.out.println("Please enter account id to withdraw");
        String id = new Scanner(System.in).nextLine();
        Integer id1 = Integer.parseInt(id);
        for (Account account : accountSet) {
            if (account.getId().equals(id1)) {
                current.setActiveAccount(account);
            } else {
                System.out.println("Incorrect id, please retry");
            }
        }
        System.out.println("Please enter sum to withdraw!");
        String sum = new Scanner(System.in).nextLine();
        try {
            BankService.withdrawAccount(current, Double.parseDouble(sum));
            BankService.saveOrUpdateClientToDB(current);
            System.out.println("Success!");
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw");
    }
}


