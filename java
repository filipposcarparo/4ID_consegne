import java.util.Date;
import java.util.concurrent.Semaphore;
import java.Random.util.*;
Class verifica{
    public static void main(String[]args){
        Queue queue= new queue();
        Thread thread[]= new Thread[10];
        for(int i = 0; i<10;i++){
        thread[i]= new Thread(new Job(queue));

        }

        for(int i = 0; i<10;i++){
        thread[i].start();
        }


    }
    class Queue{
        Semaphore semaphore;
        private Random random:
         Lock criticalSection;
        public Queue(){

        this.semaphore= new semaphore(10);
        r= new Random();
         criticalSection = new ReentrantLock();


        }
        public void stampa(){

            if(semaphore.acquirepermits()==0){
            System.out.println("Coda Piena, aspettare!");
            }   else{
            criticalSection.lock();
            try{
            semaphore.acquire();
            System.out.println("Stampa in corso...");
            Thread.sleep(1000+r.nextInt(2000));
            System.out.println(semaphore.toString());

            }catch(Exception e){
            e.printStackTrace();
            }finally{
            semaphore.release();
            criticalSection.unlock();

            }



            }

        @Override
        public String string(){
        return Arrays.toString(semaphore);

        }
        }





    }
    class Job implements Runnable{
        private Queue queue;
        public Job(Queue queue){
        this.queue= queue;
        }


    @Override
    public void run(){
        while(true){
            queue.stampa();

        }

    }



    }









}
