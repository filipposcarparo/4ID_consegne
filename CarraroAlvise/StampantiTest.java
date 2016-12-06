package stampantitest;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 @author alvise.carraro
 */
public class StampantiTest {

	public static void main(String[] args) {
		Queue q = new Queue();
		Thread j = new Thread(new Job(q));
		j.start();
		for (int i = 0; i < 10; i++) {
			Thread p = new Thread(new Printer(q));
			p.start();
		}
		

	}

}


class Queue {
	private final int QUEUE_SIZE = 3;
	private Object[] j;
	private int head, tail;
	private Semaphore mutex, full, empty;

	public Queue() {
		this.j = new Job[3];
		this.head = 0;
		this.tail = 0;
		this.mutex = new Semaphore(1);
		this.full = new Semaphore(0);
		this.empty = new Semaphore(QUEUE_SIZE);
	}

	public void insert(Job job) {
		if (full.availablePermits() == 4) {
			System.out.println("Coda piena: impossibile inserire altri Job");
		} else {
			try {
				mutex.acquire();
				empty.acquire();
				j[head] = job;
				head = (head + 1) % QUEUE_SIZE;
				System.out.println("Inserito");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				mutex.release();
				full.release();
			}
		}
	}

	public void remove() {
		if (full.availablePermits() == 0) {
			System.out.println("Coda vuota: impossibile stampare");
		} else {
			try {
				mutex.acquire();
				full.acquire();
				j[tail] = null;
				tail = (tail + 1) % QUEUE_SIZE;
				System.out.println("Tolto");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				mutex.release();
				empty.release();
			}
		}
	}

	@Override
	public String toString() {
		return "Queue{" + "QUEUE_SIZE=" + QUEUE_SIZE + ", j=" + j + ", head=" + head + ", tail=" + tail + ", mutex=" + mutex + ", full=" + full + ", empty=" + empty + '}';
	}

}

class Job implements Runnable {

	private Queue q;
	private Random r;

	public Job(Queue q) {
		this.q = q;
		r = new Random();
	}

	@Override
	public void run() {
		for (;;) {
			try {
				q.remove();
				Thread.sleep(5000 + r.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Printer implements Runnable {

	private Queue q;
	private Job j;
	private Random r;

	public Printer(Queue q) {
		this.q = q;
		j= new Job(q);
	}

	@Override
	public void run() {
		for (;;) {
			try {
				q.insert(j);
				Thread.sleep(5000 + r.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
