package main;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Bancomat extends Thread{

	private boolean autenticato;
	private ContoCorrente cc;
	
	public Bancomat(){
		autenticato = false;
		this.cc = cc;
	}
	
	@Override
	public void run(){
		
		while(!autenticato){
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(Bancomat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}	
		System.out.println("Inserire 1 per prelevare e due per depositare");
		
	}
	
	public void autentica(ContoCorrente cc,int codice){
		this.cc = cc;
		autenticato = cc.autentica(codice);
		if(autenticato){
			System.out.println("Accesso eseguito");
		}else{	
			System.out.println("Accesso negato");
		}
	}
	
	public void deposita(int somma){
		if(autenticato)
			cc.deposita(somma);
		autenticato = false;
		System.out.println("Arrivederci");
	}
	
	public void ritira(int somma){
		if(autenticato)
			cc.preleva(somma);
		autenticato = false;
		System.out.println("Arrivederci");
	}
	
}
