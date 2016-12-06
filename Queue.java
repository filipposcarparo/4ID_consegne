package stampantitest;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Queue {
    
    static final Random random = new Random();
    
    private final Semaphore semaphore;
    
    public Queue(){
        semaphore = new Semaphore(1);
    }
    
    public void printJob (Object document){
        try {
            semaphore.acquire();
            int durata = 1 + random.nextInt(4);
            System.out.printf("%s: Stampato: durata %d seconds\n",
            Thread.currentThread().getName(),durata);
            TimeUnit.SECONDS.sleep(durata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}


