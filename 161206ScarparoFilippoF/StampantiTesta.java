package stampantitesta;

import java.util.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 @author filippo.scarparo
 */
public class StampantiTesta {
	public static void main(String args[]) {
		Queue coda = new Queue();// coda di stampa
		Thread thread[] = new Thread[10];// stampati
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(new Job(coda), "Thread" + i);
		}
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
	}
}

class Job implements Runnable {

	private Queue printQueue;

	public Job(Queue printQueue) {
		this.printQueue = printQueue;
	}

	/**
	il run di joba serve per stampare il lavoro
	*/
	@Override
	public void run() {
		System.out.printf("%s:inizio stampa lavoro\n", Thread.currentThread().getName());
		printQueue.printJob(new Object());
		System.out.printf("%s: stampato\n", Thread.currentThread().getName());
	}
}

/**
questa classe gestisce le stampe 
@author filippo.scarparo
*/
class Queue {

	static final Random random = new Random((new Date()).getTime());

	private final Semaphore semaforo;
	private boolean stampantiLibere[];
	private Lock lockPrinters;

	public Queue() {
		semaforo = new Semaphore(3);
		stampantiLibere = new boolean[3];
		for (int i = 0; i < 3; i++) {
			stampantiLibere[i] = true;
		}
		lockPrinters = new ReentrantLock();
	}

	public void printJob(Object document) {
		try {
			semaforo.acquire();
			int assignedPrinter = prendoStampante();
			int durata = 1 + random.nextInt(6);
			System.out.printf("%s: coda: stampo il lavoro in coda di stampa %d durata %d in secondi\n",
			Thread.currentThread().getName(), assignedPrinter, durata);
			TimeUnit.SECONDS.sleep(durata);
			stampantiLibere[assignedPrinter] = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
	}

	/**
	questo metodo serve per sincronizzare le stampanti seguendo il FIFO
	@return 
	*/
	private int prendoStampante() {
		int ret = -1;
		try {
			lockPrinters.lock(); //inizia la sezione critica
			for (int i = 0; i < stampantiLibere.length; i++) {
				if (stampantiLibere[i]) {
					ret = i;
					stampantiLibere[i] = false; //la stampante non è libera
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lockPrinters.unlock(); //finisce la sezione critica
		}
		return ret;
	}
}
