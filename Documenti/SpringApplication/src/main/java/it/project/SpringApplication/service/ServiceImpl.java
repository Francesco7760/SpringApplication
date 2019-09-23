package it.project.SpringApplication.service;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import it.project.SpringApplication.model.Agenda;
import it.project.SpringApplication.model.ElementiUnici;
import it.project.SpringApplication.model.Metadata;
import it.project.SpringApplication.model.Stat;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
	
	String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=cgmSRTpoS2a3JdaENXV5rA";
	
	// vettore di Agenda , usata per raccogliere le Agenda costruite scansionando il file .csv
	Vector<Agenda> v = new Vector<Agenda>();
	
	// vettore di Metadata, raccoglie i metadati sugli attributi del file .csv 
	Vector<Metadata> M = new Vector<Metadata>();
	
	/*costruzione di S con parametri null e 0.
	 * dopo aver calcolato le varie statisctiche ,S verra' modificato 
	 * aggiungendo i valori statistici  */
	Stat s = new Stat("null", 0, 0, 0, 0, 0, 0);
	 
	final String DELIMITE = ",";
	 
	BufferedReader fileReader1 = null;
	
	// variabile usate per calcolare le statistiche
	int  count = 0;  
	double sum = 0;   
	double media =0;
	double max = 0;
	double min = 100; 
	double dev_std = 0; /* deviazione standard */
	double XiM = 0;     /* utile per il calcolo della deviazione standard(dev_std)*/
	int occorrenze = 0;
	
    //questi HashSet saranno utili nel trovare gli elementi unici
 	HashSet<String> setTP = new HashSet<String>();
 	HashSet<String> setRA = new HashSet<String>();
 	HashSet<String> setI = new HashSet<String>();
 	HashSet<String> setBD = new HashSet<String>();
 	HashSet<String> setUM = new HashSet<String>();
 	
 	
 	Vector<ElementiUnici> uniciEoccorrenzeTP = new Vector<ElementiUnici>();
 	Vector<ElementiUnici> uniciEoccorrenzeRA = new Vector<ElementiUnici>();
 	Vector<ElementiUnici> uniciEoccorrenzeI = new Vector<ElementiUnici>();
 	Vector<ElementiUnici> uniciEoccorrenzeBD = new Vector<ElementiUnici>();
 	Vector<ElementiUnici> uniciEoccorrenzeUM = new Vector<ElementiUnici>();
 	
 	
 	
	public ServiceImpl() {
/*porzione di codice per il parsing del JSON*/
		
		try {
		
		InputStream in = URI.create(url).toURL().openStream();
		
		 String data = "";
		 String line = "";
		 try {
		   InputStreamReader inR = new InputStreamReader( in );
		   BufferedReader buf = new BufferedReader( inR );
		  
		   while ( ( line = buf.readLine() ) != null ) {
			   data+= line;
			   
		   }
		 } finally {
		   in.close();
		 }
		JSONObject obj = (JSONObject) JSONValue.parseWithException(data); 
		JSONObject objI = (JSONObject) (obj.get("result"));
		JSONArray objA = (JSONArray) (objI.get("resources"));
		
		for(Object o: objA){
		    if ( o instanceof JSONObject ) {
		        JSONObject o1 = (JSONObject)o; 
		        String format = (String)o1.get("format");
		    
		        String urlD = (String)o1.get("url");
		        
		        
		        if(format.contains("CSV") && urlD.contains("csv") ) {
		        
		        	download(urlD, "test.csv");
			
		        }
		    }
		}
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
		
    /*porzione di codice usato per il parsing del file CSV*/ 
    try
    {
        String line = "";
        fileReader1 = new BufferedReader(new FileReader("test.csv"));
        while ((line = fileReader1.readLine()) != null)
        {
        	String[] tokens = line.split(DELIMITE);

			if(tokens[5].contains("v") ) {}
			else {
				
				Agenda p = new Agenda(tokens[0].replace("\"",""),tokens[1].replace("\"",""),tokens[2].replace("\"",""),tokens[3].replace("\"",""),tokens[4].replace("\"",""),Double.parseDouble(tokens[5].replace("\"","")));
			    v.add(p);}			
        }

       }        
    catch (Exception e) {
        e.printStackTrace();
    }
    finally
    {
        try {
            fileReader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*dopo aver concluso il parse del JSON e il parse del file .csv, inizia a lavorare sulle staistiche 
     * degli attributi numerici (nel nostro esempio abbiamo solo "value" come attributo numerico).
     * una volta finito il calcolo delle varie statistiche  , si va a popolare l'oggetto s (della classe Stat)*/
    
    //ciclo per il count
    for(Agenda item: v) {		
    	count++;
    }
    //ciclo per il minimo
    for(int i=0;i<=count-1;i++) {
    	if (v.get(i).getValue()< min)
    		min = v.get(i).getValue();
    		
    	
    }
    //ciclo per max
    for(int i=0;i<=count-1;i++) {
    	if(v.get(i).getValue()> max) {
    		max = v.get(i).getValue();
    	}
    	
    }
    //ciclo per sum 
    for(int i=0;i<=count-1;i++) {
    	sum += v.get(i).getValue();	
    }
    //calcolo della media
    double media = sum/count;
    
    //calcolo della deviazione standard
    for(int i=0;i<=count-1;i++){
    	XiM += Math.pow(v.get(i).getValue() - media, 2);//  Xi-M
    	
    }dev_std = Math.sqrt(XiM/(count-1));
    
    /*tramite i setter della classe Stat, modifico s */
    s.setField("value");
    s.setAvg(media);
    s.setCount(count);
    s.setDev_std(dev_std);
    s.setMin(min);
    s.setMax(max);
    s.setSum(sum);
    
	/* viene costruito un oggetto per ogni attributo 
	 * ciascuno oggetto conterra: 1)nome attributo
	 * 							  2)piccola descrizoine del attribuot
	 * 							  3)tipo restituito
	 * 
	 *in fine i metadati verranno inseriti nel vettore M*/
    
    Metadata time_period = new Metadata("time_period", "anno con relativo quadrimestre", "String");
    M.add(time_period);
    Metadata ref_area = new Metadata("ref_area", "riferimento del area geografica", "String") ;
    M.add(ref_area);
    Metadata indicatore = new Metadata("indicator", "indicator", "String");
    M.add(indicatore);
    Metadata breakdown = new Metadata("breakdown", "breakdown", "String");
    M.add(breakdown);
    Metadata unit_measure = new Metadata("unit_measure", "unita di misura", "String");
    M.add(unit_measure);
    Metadata value = new Metadata("value", "valore", "double");
    M.add(value);
    
    
    /*il seguente ciclo for sfrutta la caratteristica dei HashSet,  
     *che permettono di immagazzinare solo elementi distinti.
     *cosi da avere nelle ripsettive HashSet gli elementi unici dei singoli attributi */
    
    for(Agenda item: v) {
    	setTP.add(item.getTime_period());
    	setRA.add(item.getRef_area());
    	setI.add(item.getIndicator());
    	setBD.add(item.getBreak_down());
    	setUM.add(item.getUnit_measure());
      }
  }

	@Override
	public void download(String url, String file_name) {
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		    FileOutputStream fileOutputStream = new FileOutputStream(file_name);
		    byte dataBuffer[] = new byte[1024];
		    int byteRead;
		    while((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
		    	fileOutputStream.write(dataBuffer, 0, byteRead);
		    }}catch(IOException e) {
		    	e.printStackTrace();
		    }		
	}

	@Override
	public Agenda getAgenda(int id) {
		return v.get(id);
	}

	@Override
	public Vector<Agenda> getVector() {
		return v;
	}

	@Override
	public double media() {
		return media;
	}

	@Override
	public double sum() {
		return sum;
	}

	@Override
	public double max() {
		return max;
	}

	@Override
	public double min() {
		return min;
	}

	@Override
	public double dev_std() {
		return dev_std;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public Stat getStat() {
		return s;
	}

	@Override
	public Vector<Metadata> getMetadata() {
		return M;
	}

	@Override
	public String errore() {
		return "errore";
	}

	@Override
	public HashSet<String> timeperiodunici() {
		return setTP;
	}

	@Override
	public HashSet<String> refareaunici() {
		return setRA;
	}

	@Override
	public HashSet<String> indicatorunici() {
		return setI;
	}

	@Override
	public HashSet<String> breakdowndunici() {
		return setBD;
	}

	@Override
	public HashSet<String> unitmeasureunici() {
		return setUM;
	}
	
	@Override
	public Vector<ElementiUnici> occorrenzeEstringaTP() {
		for(String s: setTP) {
	    	int freq =0;
	    	for(int i=0;i<count;i++) {
	    		if(s.equals(v.get(i).getTime_period())) {
	    			freq++;
	    		}
	    	}ElementiUnici UNICItp = new ElementiUnici(s,freq);
	    	uniciEoccorrenzeTP.add(UNICItp);
	    }
		return uniciEoccorrenzeTP;
	}
	
	@Override
	public Vector<ElementiUnici> occorrenzeEstringaRA() {
		 for(String s: setRA) {
		    	int freq =0;
		    	for(int i=0;i<count;i++) {
		    		if(s.equals(v.get(i).getRef_area())) {
		    			freq++;
		    		}
		    	}ElementiUnici UNICItp = new ElementiUnici(s,freq);
		    	uniciEoccorrenzeRA.add(UNICItp);
		    }
			return uniciEoccorrenzeRA;
	}
	
	@Override
	public Vector<ElementiUnici> occorrenzeEstringaI() {
		 for(String s: setI) {
		    	int freq =0;
		    	for(int i=0;i<count;i++) {
		    		if(s.equals(v.get(i).getIndicator())) {
		    			freq++;
		    		}
		    	}ElementiUnici UNICItp = new ElementiUnici(s,freq);
		    	uniciEoccorrenzeI.add(UNICItp);
		    }
			return uniciEoccorrenzeI;
	}
	
	@Override
	public Vector<ElementiUnici> occorrenzeEstringaBD() {
		 for(String s: setBD) {
		    	int freq =0;
		    	for(int i=0;i<count;i++) {
		    		if(s.equals(v.get(i).getBreak_down())) {
		    			freq++;
		    		}
		    	}ElementiUnici UNICItp = new ElementiUnici(s,freq);
		    	uniciEoccorrenzeBD.add(UNICItp);
		    }
			return uniciEoccorrenzeBD;
	}
	
	@Override
	public Vector<ElementiUnici> occorrenzeEstringaUM() {
		 for(String s: setUM) {
		    	int freq =0;
		    	for(int i=0;i<count;i++) {
		    		if(s.equals(v.get(i).getUnit_measure())) {
		    			freq++;
		    		}
		    	}ElementiUnici UNICItp = new ElementiUnici(s,freq);
		    	uniciEoccorrenzeUM.add(UNICItp);
		    }
			return uniciEoccorrenzeUM;
	}
}