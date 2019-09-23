progetto esame programmazione oggetti 
===================
studente: Francesco Di Bernardo 

dataset JSON assegnatomi: http://data.europa.eu/euodp/data/api/3/action/package_show?id=cgmSRTpoS2a3JdaENXV5rA

In questa pagina verrà descritto il funzionamento del programma, cosi da facilitare la comprensione.
Nella prima parte del documento verranno spiegate le varie funzionalità implementate.
La seconda parte, esporrà le modalità implementative delle statistiche.
In fine saranno presentate le funzionalita del programma restituite sfruttando SpringBoot. 

----------


Prima parte
----------------

Il programma al avvio effettua una decodifica del JSON, cosi da ottenere il dataset desiderato in formato .csv.
Una volta individuata l'URL associata al csv , il programma effettua il download del file.
Il file sarà scaricato nella directory principale , si chiamerà  **test.csv** .
A download concluso, verrà effettuato il parsing di **test.csv**, ad ogni riga del file verrà creato un oggetto della classe Agenda.
Durante la creazione e popolazione dei vari oggetti, gli elementi del attributo **"value"** vengo convertiti da stringa a numeri (double).
Ciascun oggetto verrà aggiunto al vettore **v**. 

Per la analisi del dataset JSON, viene modificato il file pom.xml per introdurre la dipendenza a *json-simple*.

> #####Il dataset .csv è costituito da 6 attributi
> - "time_period"
> - "ref_area"
> - "indicator"
> - "break_down"
> - "unit_measure"
> - "value"

Una volta concluse la analisi del dataset e la creazione dei vari oggetti,   il programma si concentra sulla implementazione delle statistiche per gli attributi numerici e stringhe.


Seconda parte
------------

> **"statistiche" stringhe - attributi "value"**

> - *count* : variabile intera. Rappresenta il numero di elementi.
> - *min* : variabile double. Rappresenta l'elemento di valore minimo.
> - *max* : variabile double. Rappresenta l'elemento di valore massimo.
> - *sum* : variabile double. Rappresenta la somma dei valori degli elementi.
> - *media* : variabile double. Rappresenta la media dei valori.
> - *dev_std* : variabile double. Rappresenta la deviazione standard.


Una volta concluso il calcolo delle statistiche, vengono raccolte in un vettore. 

Nel caso di attributi costituiti da stringhe le "statistiche" da implementare saranno diverse.

> **statistiche numeriche - attributi "time_period", "ref_area", "indicator", "breakdown", "unit_measure"**

> - determinare gli elementi unici per ogni attributo.
> -  per ogni elemento unico determinare il numero di occorrenze.

Per determinare gli elementi unici dei singoli attributi , viene sfruttana la caratteristica dei HashSet, quella di immagannizare solo elementi distinti tra loro.
Tramite un ciclo for vengono almentati gli HshSet con le stringhe dei singoli attributi.
A fine ciclo si otterranno 5 insiemi contenenti gli elementi unici.
 
Il modo con cui vengono trovate le occorrenze è spiegato tramite un esmpio.

##### es: *trovare le occorrenze degli elementi unici in time_period*
Una volta aver popolato l'HashSet **"setTP"**, sarà il metodo **"public Vector<ElementiUnici> occorrenzeEstringaTP();"** a restituire gli elementi unici e le rispettive occorrenze, raccolte nel vettore **"uniciEoccorrenzeTP"**.


Terza parte
----------------
Il programma usando SpringBoot tramite rotte distinte restituisce le proprie funzionalità.

> **"rotte distinte e cio che restituiscono"**

> - **/agenda**  : la rotta */agenda* restituisce tutti i dati presente nel dataset .csv, i dati sono presentati in formato JSON
> -  **/agenda/{id}** : la rotta */agenda/{id}* restituisce il singolo elemento di indice id, in formato JSON. Se sara inserito un *id* maggiore del numero di elementi, verrà stampato errore.
> -  **/statvalue** : la rotta */statvalue* restituisce tutte le statistiche del attributo numerico, in formato JSON
> - **/metadata** : la rotta */metadata* restituisce i metadati di tutti gli attributi, in formato JSON
> - **/metadata/{string}** : la rotta */metadata/{string}* restituisce i metadati di un singolo attributo, in formato JSON.
L'attributo che ci interessa sara inserito in *string*
es. */metada/time_period* restituisce i metadati di time_period       
 > - **/unici/{sring}** : la rotta */unici/{string}* restituisce gli element unici, in formato JSON, presenti nel attributo specificato in *string*.
 es. */unici/time_period* restituisce gli elementi unici di time_period
 > - **/occorrenze/{string}** : la rotta */occorrenze/{string}* restituisce gli element unici, in formato JSON, e le relative occorrenze presenti nel attributo specificato in *string*.
 es. */occorrenze/time_period* restituisce gli elementi unici di time_period e le relative occorrenze.



