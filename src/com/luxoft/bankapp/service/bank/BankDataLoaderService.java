package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.ui.BankCommander;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 26.02.14.
 */
public class BankDataLoaderService {
    public void loadFeed(String folder) {
        if (BankCommander.getActiveBank() != null) {
            File file = new File(folder);
            File[] files = file.listFiles();
            for (File f : files) {
                try {
                    BufferedReader bfr = new BufferedReader(new FileReader(file));
                    while (true) {
                        String line = bfr.readLine();
                        if (line == null) break;
                        Map<String, String> map = new HashMap<String, String>();
                        String[] splitted = line.split(";");
                        for (String s : splitted) {
                            String[] paires = s.split("=");
                            map.put(paires[0], paires[1]);
                        }
                        BankCommander.getActiveBank().parseFeed(map);
                    }
                    bfr.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClientExistsException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Please choose the bank!");
        }
    }
}
