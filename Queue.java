package stampantitest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Queue implements Runnable {
	
	private final Semaphore semaforo;

	public Queue(Semaphore semaphore) {
		this.semaforo = semaphore;
	}

	@Override
	public void run() {
		try {
			semaforo.acquire();
			Thread.currentThread().getName();
			TimeUnit.SECONDS.sleep(2);
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			semaforo.release();
		}
	}
	
	
}
