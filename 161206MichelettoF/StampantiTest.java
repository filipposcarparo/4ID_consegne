package stampantitest;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 @author christian.micheletto
 */
public class StampantiTest {

	public static void main(String[] args) throws InterruptedException {
		Queue coda = new Queue();
		Thread[] stampanti = new Thread[10];
		for (int i = 0; i < stampanti.length; i++) {
			stampanti[i] = new Thread(new Job(coda), "Thread " + i);
		}
		for (int i = 0; i < stampanti.length; i++) {
			stampanti[i].start();
			TimeUnit.SECONDS.sleep(1);
		}
	}

}

class Queue {

	private Random random = new Random();
	private Semaphore s = new Semaphore(3);

	public Queue() {

	}

	public void print(Object foglio) {

		try {
			s.acquire();
			int tempoStampa = random.nextInt(5);
			System.out.println("Stampo il foglio del Thread " + Thread.currentThread().getName()
					+ " in " + tempoStampa + " secondi");
			TimeUnit.SECONDS.sleep(tempoStampa+5);

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			s.release();

		}
	}

}

class Job implements Runnable {

	private Queue coda;

	public Job(Queue coda) {
		this.coda = coda;
	}

	@Override
	public void run() {
		System.out.println("Invio la stampa del Thread: " + Thread.currentThread().getName());
		coda.print(new Object());
		System.out.println("Foglio del Thread " + Thread.currentThread().getName() + " stampato");

	}

}
