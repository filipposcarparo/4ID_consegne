package contocorrenrtetest;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 @author leonardo.bigetti
 */
public class ContoCorrenrteTest {

	public static void main(String[] args) {
		Random gen = new Random();
		ThreadGroup g = new ThreadGroup("soggetti");
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(g,new Correntista(new Bancomat(gen.nextInt(10000), gen.nextInt(10000) + 1, gen.nextInt(1) + 1)), i + "");
			t.start();
			try {
				TimeUnit.SECONDS.sleep(gen.nextInt(5) + 1);

			} catch (InterruptedException e) {

			}
		}

	}

}

class Bancomat {
	public static final Random gen = new Random();
	Semaphore bancomat;
	int denaroIniziale;
	int denaro;
	int i;
	int codice;
	Lock lucchetto;
	static final int codiceScorreto = 12345;

	public Bancomat(int codice, int denaro, int i) {
		this.bancomat = new Semaphore(3);
		this.codice = codice;
		this.denaroIniziale = 100000;
		lucchetto = new ReentrantLock();
		this.denaro = denaro;
		this.i = i;

	}

	public void prelievoDeposito() {
		if (this.codice != this.codiceScorreto) {
			System.out.println("acceso eseguito");
			try {
				this.bancomat.acquire();
				System.out.println("digitare 1 se si desidera prelevare, 2 per depositare");
				lucchetto.lock();
				if (i == 1) {
					System.out.println("inserire la cifra che si vuole prelevare");
					try {
						TimeUnit.SECONDS.sleep(gen.nextInt(1) + 1);

					} catch (InterruptedException e) {

					}
					System.out.println("operazione eseguita");
				} else {
					System.out.println("inserire la cifra che si vuole depositare");
					if (this.denaroIniziale - denaro > 0) {
						this.denaroIniziale = -denaro;
						try {
							TimeUnit.SECONDS.sleep(gen.nextInt(1) + 1);

						} catch (InterruptedException e) {

						}
						System.out.println("operazione eseguita");
					} else {
						System.out.println("conto in rosso impossibile prelevare il denaro");
					}

				}
				System.out.println("grazie e arrivederci");
				lucchetto.unlock();
			} catch (InterruptedException e) {
				//non entrera mai
			} finally {
				this.bancomat.release();
			}
		} else {
			System.out.println("codice scorretto");
		}

	}

}

class Correntista implements Runnable {

	Bancomat b;

	public Correntista(Bancomat b) {
		this.b = b;
	}

	@Override
	public void run() {
		System.out.println("sono il soggetto " + Thread.currentThread().getName());
		b.prelievoDeposito();
		System.out.println(" ");
	}

}
