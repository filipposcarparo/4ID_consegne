package stampantitest;

public class Job implements Runnable{
	
	private Queue coda;
	private int job1;
	private int job2;
	private int job3;

	public Job(Queue coda, int job1, int job2, int job3) {
		this.coda = coda;
		this.job1 = job1;
		this.job2 = job2;
		this.job3 = job3;
	}

	@Override
	public void run() {
		try{
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
