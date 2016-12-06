package stampantitets;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author dylan.vanzan
 */
public class StampantiTets {

    public static void main(String[] args) {
        
		Queue q = new Queue();
        Thread thread[] = new Thread[10];
        for (int i=0; i<10; i++){
            thread[i] = new Thread(new Job(q),"Thread"+i);
        }
        for (int i=0; i<10; i++){
            thread[i].start();
        }
		
    }

}

class Queue {
    
    static final Random random = new Random((new Date()).getTime());
    
    private final Semaphore s;
    
    public Queue(){
        s = new Semaphore(1);
    }
    
    public void stampaJob (Object document){
        try {
            s.acquire();
            int duration = 1 + random.nextInt(5);
            System.out.printf("%s: Stampa lavoro: %d secondi\n",
            Thread.currentThread().getName(),duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            s.release();
        }
    }
}

class Job implements Runnable {
    
    private Queue q;
    
    public Job(Queue q){
       this.q = q;
    }
    
    @Override
    public void run() {
        System.out.printf("%s: stampando lavoro:\n", Thread.currentThread().getName());
        q.stampaJob(new Object());
        System.out.printf("%s: stampato:\n", Thread.currentThread().getName());
    }
}
        
    
    

