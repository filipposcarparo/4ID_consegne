La mia idea é che:
- esiste sola una coda di stampa;
- esistono 10 stampanti che posso contenere al massimo 3 lavori, ho gestito con un semaforo per prendere i permessi di stampa e un Lock
per far uscire in odine le stampe ovvero FIFO.
- ho fatto in tutto 3 classi;
- dentro la classe queue ho fatto il metodo PrintJob(ovvero quello che stampa il lavoro) e prendoStampante per gestire con FIFO
