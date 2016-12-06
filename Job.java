package stampantitest;

class Job implements Runnable {
    
    private  Queue printQueue;
    
    public Job(Queue printQueue){
       this.printQueue = printQueue;
    }
    
	@Override
    public void run() {
        System.out.printf("%s: Aggiunto a coda di stampa\n", Thread.currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: Stampa in corso\n", Thread.currentThread().getName());
    }
}