package main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContoCorrente {

	private int saldo;
	private final Lock autorizzazione;
	private final int codiceAccesso;
	
	public ContoCorrente(int codiceAccesso){
		saldo = 0;
		autorizzazione = new ReentrantLock();
		this.codiceAccesso = codiceAccesso;
	}
	
	boolean autentica(int codice){
		return codice != codiceAccesso;
	}
	
	public void preleva(int somma){
		autorizzazione.lock();
		try {
			saldo -= somma;
		} finally {
			autorizzazione.unlock();
			System.out.println("Operazione eseguita");
		}
	}
	
	public void deposita(int somma){
		autorizzazione.lock();
		try {
			saldo += somma;
		} finally {
			autorizzazione.unlock();
			System.out.println("Operazione eseguita");
		}
	}

	
}
