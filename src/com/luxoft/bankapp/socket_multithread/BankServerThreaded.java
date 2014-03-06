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
    private final int POOL_SIZE = 5;
    private final boolean running = true;
    private AtomicInteger counter = new AtomicInteger(0);

    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public BankServerThreaded() throws IOException {
        serverSocket = new ServerSocket(PORT);
        pool = Executors.newFixedThreadPool(POOL_SIZE);
    }

    public void serve() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                counter.incrementAndGet();
                pool.execute(new ServerThread(clientSocket));
                counter.decrementAndGet();

            } catch (IOException e) {
                pool.shutdown();
            }
        }
    }
}