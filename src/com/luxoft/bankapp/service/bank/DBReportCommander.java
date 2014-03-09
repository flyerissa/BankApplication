package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by aili on 24.02.14.
 */
public class DBReportCommander implements Command {

    @Override
    public void execute() throws Exception {
        try {
            System.out.println("Enter name of the bank!");
            Scanner sc = new Scanner(System.in);
            final String name = sc.nextLine();
            TransactionManager tm = TransactionManager.getInstance();
            BankInfo bankInfo = tm.doInTransaction(new Callable<BankInfo>() {
                @Override
                public BankInfo call() throws Exception {
                    return BankService.getInstance().getBankInfo(name);
                }
            });

            System.out.println("Info for bank " + BankCommander.getActiveBank().getName() +
                    "\n Number of clients is: " + bankInfo.getNumberOfClients()
                    + "\n total account sum is - " + bankInfo.getTotalAccountSum() +
                    "\n list of clients, sorted by city: " + bankInfo.getClientsByCity());
        } catch (BankInfoException e) {
            e.getMessage();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Print report with info about current bank");
    }
}
