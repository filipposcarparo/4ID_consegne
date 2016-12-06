package main;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Correntista extends Thread{
	
	private final int codiceAccesso;
	private final ContoCorrente cc;
	
	public Correntista(int codiceAccesso, ContoCorrente cc){
		this.codiceAccesso = codiceAccesso;
		this.cc = cc;
	}

	public int getCodiceAccesso(){
		return codiceAccesso;
	}
	
	@Override
	public void run(){
		
		Random gen = new Random();
		while(true){
			try {
				TimeUnit.SECONDS.sleep(gen.nextInt(3)+2);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			Bancomat bancomat = new Bancomat();
			bancomat.start();
			bancomat.autentica(cc, codiceAccesso);
			if(gen.nextInt(1)==0){
				bancomat.ritira(gen.nextInt(100));
			}else{
				bancomat.deposita(gen.nextInt(100));
			}
		
			
		}
	}
	
}
