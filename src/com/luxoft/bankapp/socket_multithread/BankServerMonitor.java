package com.luxoft.bankapp.socket_multithread;

/**
 * Created by User on 06.03.14.
 */
public class BankServerMonitor implements Runnable {
    private boolean run = true;
    private BankServerThreaded executor;


    public BankServerMonitor(BankServerThreaded executor) {
        this.executor = executor;
    }

    public void shutdown() {
        this.run = false;
    }

    @Override
    public void run() {
        while (run) {
            try {
                System.out.println(BankServerThreaded.getCounter());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
