package main;

import java.util.Random;

/**
 * @author marco.tramontini
 */
public class ContoCorrenteTest {


	
    public static void main(String[] args) {
		ContoCorrente[] conti = new ContoCorrente[3];
         conti[0] = new ContoCorrente(12345);
	     conti[1] = new ContoCorrente(54321);	 
		 conti[2] = new ContoCorrente(99999);	
		 
		 Random gen = new Random();
		 
		 int[] codici = {12345,54321,99999};
		 
		 for(int i =0 ;i <10;i++){
			 new Correntista(codici[gen.nextInt(3)],conti[gen.nextInt(3)]).start();
		 }
		 
    }

}
