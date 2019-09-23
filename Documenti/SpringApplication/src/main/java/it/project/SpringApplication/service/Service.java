package it.project.SpringApplication.service;

import java.util.HashSet;
import java.util.Vector;

import it.project.SpringApplication.model.Agenda;
import it.project.SpringApplication.model.ElementiUnici;
import it.project.SpringApplication.model.Metadata;
import it.project.SpringApplication.model.Stat;

public interface Service {
	    // interfaccia del metodo usato per fare il download del file .csv
		public abstract void download(String url, String file_name);
		// restituisce un ogetto Agenda desiderato , dove id e' la pozione 
		public abstract Agenda getAgenda(int id);
		// restituisce il vettore contenente tutti gli ogetti Agenda
		public abstract Vector<Agenda> getVector();
		// restistuisce il valore della media
		public abstract double media(); 
		// restituisce il valore somma 
		public abstract double sum();
		// restituisce il valore massimo
		public abstract double max();
		// restistuisce il valore minimo
		public abstract double min();
		// restistuisce il valore della deviazione standard
		public abstract double dev_std();
		// restistuisce il numero di Product costruiti
		public abstract int count();
		// restituisce tutte le statistiche 
		public abstract Stat getStat();
		// restituisce il vettore contenente il metadata degli attributi
		public abstract Vector<Metadata> getMetadata();
		// restituisce la stringa "errore"
		public abstract String errore();
		
		
	
		//caso estremo
		public abstract HashSet<String> timeperiodunici();
		public abstract HashSet<String> refareaunici();
		public abstract HashSet<String> indicatorunici();
		public abstract HashSet<String> breakdowndunici();
		public abstract HashSet<String> unitmeasureunici();
		
		public abstract Vector<ElementiUnici> occorrenzeEstringaTP();
		public abstract Vector<ElementiUnici> occorrenzeEstringaRA();
		public abstract Vector<ElementiUnici> occorrenzeEstringaI();
		public abstract Vector<ElementiUnici> occorrenzeEstringaBD();
		public abstract Vector<ElementiUnici> occorrenzeEstringaUM();

}
