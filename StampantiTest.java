package stampantitest;

/**
 * @author luca.modolo
 */
public class StampantiTest {

    public static void main(String[] args) {
        
        Queue queue = new Queue();
        Thread thread[] = new Thread[10];
        for (int i=0; i<10; i++){
            thread[i] = new Thread(new Job(queue,1,2,3),"Thread"+i);
        }
        for (int i=0; i<10; i++){
            thread[i].start();
        }
    }
}