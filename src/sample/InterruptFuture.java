package sample;

import java.util.Random;
import java.util.concurrent.*;

public class InterruptFuture {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting.");

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<?> future = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Random random = new Random();

                for (int i = 0; i < 1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("We've been interrupted.");
                        break;
                    }

                    Math.sin(random.nextDouble());
                }
                return null;
            }
        });

        executor.shutdown();

        Thread.sleep(500);

        executor.shutdownNow(); // interrupt
        //future.cancel(true); // interrupt

        executor.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("Finished.");
    }

}
