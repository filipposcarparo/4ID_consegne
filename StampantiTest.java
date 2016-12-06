package stampantitest;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author andrea.zoccarato
 */
public class StampantiTest {

    public static void main(String[] args) {
        Thread []t=new Thread[10];
		Queue coda=new Queue();
		for (int i = 0; i < 10; i++) {
			t[i]=new Thread(new Job(coda));
		}
		for (int i = 0; i < 10; i++) {
			t[i].start();
		}
    }

}

class Queue{
	private Lock CriticalSection;
	private boolean Printers[];
	private Semaphore coda;

	public Queue() {
		this.CriticalSection = new ReentrantLock();;
		this.Printers = new boolean [10];
		Arrays.fill(Printers, true);
		coda=new Semaphore(3);
	}
	
	public void printJob(){
		try{
			coda.acquire();
			int stampante=getPrinter();
			Printers[stampante]=true;
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			coda.release();
		}
	}
	
	public int getPrinter(){
		try{
		this.CriticalSection.lock();
		for (int i = 0; i < Printers.length; i++) {
			if(Printers[i]){
				Printers[i]=false;
				return i;
			}
		}
		return -1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally{
		this.CriticalSection.unlock();
		}
	}
}

class Job implements Runnable{

	private Queue coda;

	public Job(Queue coda) {
		this.coda = coda;
	}
	
	@Override
	public void run() {
        coda.printJob();
	}
	
}