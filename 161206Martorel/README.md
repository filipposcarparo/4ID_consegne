# Martorel Devid
## Problema F

### Queue

Per la risoluzione del problema sono state adottate 2 classi;
la prima classe Queue è una classe che conitne la variabile N-PROCESSI; la quale è una variabile utilizzata dal thread per sapere di quanti processi di stampa comporre la coda; questo valore viene assegnato tramite il costruttore.

la seconda variabile, N_PROCESSI_CONTEMPORANEI,  è una costante che definisce il numero di processi di stampa attivi contemporaneamente.

##### Run

Il metodo run genera un semaforo da N_PROCESSI_CONTEMPORANEI  utilizzato per gestire la coda di stampa;
tale semaforo verrà passato ad ogni thred job che verrà creato per permettergli di sapere se è disponibile l'accesso alla stampante o meno.
Successivamente in un primo for vegono creati N_PROCESSI i quali vengono avviati da un secondo for.
Ogni processo quindi tenterà di terminare ma verrà bloccato dal semaforo.

### Job
La classe Job contiene invece una  variabile la quale è una copia del sempaforo che viene passato al momento della costruzione.
Dato che in java quando si passano gli oggetti si passano l'indirizzo di memoria, i vari thread job faranno tutti a capo dello stesso semaforo.
Successivamente si trova una costante TEMPO_DI_STAMPA che definisce il tempo di stampa in ms.

##### Costruttore
nel cotruttore viene aquisita la copia del semaforo e il nome del processo

##### Run 

Il metodo run inizia il try con l'aquisizione dal semaforo, in caso di successa aquisizione il thread con un sout avviserà dell'inzio di stampa e simulerà una stampa dalla durata di  TEMPO_DI_STAMPA ms attraverso uno sleep.
al termine della stampa avviserà della sua conclusione e procederà con il rilascio di un unità del semaforo.
in caso di interrompimento del thread verrà visualizzato un messaggio di fallimento di stampa ma comunque sarà rilasciato il semaforo contando che l'azoine è scritta in un finally. 
