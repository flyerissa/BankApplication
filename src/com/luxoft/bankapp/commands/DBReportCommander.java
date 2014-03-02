package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.ui.BankCommander;

/**
 * Created by aili on 24.02.14.
 */
public class DBReportCommander implements Command {

    @Override
    public void execute() {
        try {
            BankInfo bankInfo = BankService.getBankInfo();
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
