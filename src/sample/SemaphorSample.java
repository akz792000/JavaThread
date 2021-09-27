package sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Connection {

    private static Connection instance = new Connection();

    private Semaphore sem = new Semaphore(10, true);

    private int connections = 0;

    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current connection: " + connections);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
    }
}

public class SemaphorSample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) {
            executor.submit(() -> {
                Connection.getInstance().connect();
            });
        }

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
