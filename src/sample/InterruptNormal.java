package sample;

import java.util.Random;

public class InterruptNormal {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting.");

        Thread thread = new Thread(() -> {
            Random random = new Random();

            for (int i = 0; i < 1E8; i++) {
                /*
                // to catch the interrupted exception we can use Sleep
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("We've been interrupted.");
                    break;
                }
                */

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("We've been interrupted.");
                    break;
                }

                Math.sin(random.nextDouble());
            }
        });

        thread.start();

        Thread.sleep(500);

        // it does not kill the thread
        thread.interrupt();

        thread.join();

        System.out.println("Finished.");
    }

}
