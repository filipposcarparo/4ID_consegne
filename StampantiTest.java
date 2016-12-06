package stampantitest;

/**
 * @author lorenzo.manassei
 */
public class StampantiTest {
    
    public static void main (String args[]){
        
        Queue coda = new Queue();
        Thread thread[] = new Thread[10];
        for (int i=0 ; i<10; i++ ){
            thread[i] = new Thread(new Job(coda),"Thread"+i);
        }
        for (int i=0; i<10;i++ ){
            thread[i].start();
        }
    }
}




        
    
    
