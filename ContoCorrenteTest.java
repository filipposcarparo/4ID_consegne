package contocorrentetest;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 @author paolo.pavanello
 */
public class ContoCorrenteTest {

	public static int[][] codici = new int[][]{{1, 2, 3, 4, 5}, {4, 3, 2, 1, 5}, {6, 4, 8, 4, 1}};

	public static void main(String[] args) {

		ContoCorrente[] conti = new ContoCorrente[3];

		conti[0] = new ContoCorrente(new int[]{1, 2, 3, 4, 5});
		conti[0] = new ContoCorrente(new int[]{4, 3, 2, 1, 5});
		conti[0] = new ContoCorrente(new int[]{6, 4, 8, 4, 1});

		Thread[] correntisti = new Thread[10];
		for (int i = 0; i < 10; i++) {
			System.out.printf("All correntista %d è stato assegnato il conto %d \n", i, (i - 1) / 3);
			correntisti[i] = new Thread(new Correntista(conti[(i - 1) / 3]), "Correntista " + i);
		}

		for (int i = 0; i < 10; i++) {
			correntisti[i].start();
		}
	}

}

class Correntista implements Runnable {

	Random RAND;
	ContoCorrente conto;
	String nome;

	public Correntista(ContoCorrente c) {
		conto = c;
		RAND = new Random();
	}

	@Override
	public void run() {
		nome = Thread.currentThread().getName();
		while (true) {
			try {
				conto.occupy();
				System.out.println(nome + ": Inserire codice");
				if (conto.signIn(ContoCorrenteTest.codici[RAND.nextInt(3)])) {
					//Acesso Eseguito
					System.out.println(nome + ": Accesso eseguito");
					System.out.println(nome + ": Digitare 1 se si desidera prelevare, 2 per depositare");
					//Scelgo se prelavare o depositare
					if (RAND.nextInt(2) == 0) {
						conto.preleva(1 + RAND.nextInt(9));
						System.out.println(nome + ": Inserire la cifra da prelevare");
						System.out.println(nome + ": Operazione eseguita \nArrivederci");
					} else {
						conto.deposita(1 + RAND.nextInt(9));
						System.out.println(nome + ": Inserire la cifra da depositare");
						System.out.println(nome + ": Operazione eseguita \nArrivederci");
					}
				} else {
					System.out.println(nome + ": Accesso negato");
				}
				TimeUnit.SECONDS.sleep(3 + RAND.nextInt(2));
			} catch (Exception e) {
			}
		}
	}
}

class ContoCorrente {

	int[] code;
	int saldo;
	Lock accesso;

	public ContoCorrente(int[] c) {
		code = c;
		saldo = 0;
		accesso = new ReentrantLock();
	}

	public void occupy() {
		accesso.lock();
	}

	public boolean signIn(int[] c) {
		if (Arrays.equals(c, code)) {
			return true;
		} else {
			accesso.unlock();
			return false;
		}
	}

	public void preleva(int ammount) {
		try {
			//Se Il lock è gia ottenuto  il programma continua senza aspettare
			saldo -= ammount;
		} catch (Exception e) {
		} finally {
			accesso.unlock();
		}
	}

	public void deposita(int ammount) {
		try {
			//Se Il lock è gia ottenuto  il programma continua senza aspettare
			saldo += ammount;
		} catch (Exception e) {
		} finally {
			accesso.unlock();
		}
	}
}
