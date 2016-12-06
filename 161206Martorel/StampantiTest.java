package stampantitest;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author devid.martorel
 */
public class StampantiTest {

    public static void main(String[] args) {
        Thread codaDiStampa = new Thread(new Queue(10));
		codaDiStampa.start();
    }

	public static class Queue extends Thread{
		private int N_STAMPANTI;
		
		public Queue(int n){
			N_STAMPANTI=n;
		}
		
		@Override
		public void run(){
			Semaphore coda=new Semaphore(3);
			Thread[] stampanti = new Thread [N_STAMPANTI];
			for (int i = 0; i < N_STAMPANTI; i++) {
				stampanti[i]=new Job(coda,Integer.toString(i));
			}for (int i = 0; i < N_STAMPANTI; i++) {
				stampanti[i].start();
			}
		}
		
	}
	
	public static class Job extends Thread{
		
		Semaphore s;
		private static final long TEMPO_DI_STAMPA=3000;
		
		public Job(Semaphore via,String i){
			s=via;
			this.setName(i);
		}
		
		@Override
		public void run(){
			try{
				s.acquire();
				System.out.println("Processo n° "+this.getName()+" stampa in corso");
				Thread.sleep(TEMPO_DI_STAMPA);
				System.out.println("Processo n° "+this.getName()+" stampa Completata");
			} catch (InterruptedException ex) {
				System.out.println("Processo n° "+this.getName()+" stampa fallita");
			}finally{
				s.release();
		}
		}
		
	}
	
}

