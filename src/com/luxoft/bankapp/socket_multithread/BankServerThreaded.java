package com.luxoft.bankapp.socket_multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 06.03.14.
 */
public class BankServerThreaded {
    private final int PORT = 8080;
    private final int POOL_SIZE = 3;

    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static AtomicInteger getCounter() {
        return counter;
    }

    public BankServerThreaded() throws IOException {
        serverSocket = new ServerSocket(PORT);
        pool = Executors.newFixedThreadPool(POOL_SIZE);
    }

    public void serve() {
        try {
            Thread t = new Thread(new BankServerMonitor(this));
            t.setDaemon(true);
            t.start();
            System.out.println("Waiting for connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println(counter.incrementAndGet());
            pool.execute(new ServerThread(clientSocket));
        } catch (IOException e) {
            pool.shutdown();
        }
    }


    public static void main(String[] args) {
        try {
            BankServerThreaded bankServerThreaded = new BankServerThreaded();

            bankServerThreaded.serve();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}