package it.project.SpringApplication.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.project.SpringApplication.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class Restcontroller {
	@Autowired
	Service service;
	
	@RequestMapping (value= "/" , method = RequestMethod.GET)
	@ResponseBody
	public String presentazione() {
		return "SALVE!!";
	}

	@RequestMapping (value= "/agenda", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getVector(){
		return new ResponseEntity<>(service.getVector(),HttpStatus.OK);
	}
	@RequestMapping (value= "/agenda/{id}", method = RequestMethod.GET)
	@ResponseBody
		public ResponseEntity<Object> getProduct(@PathVariable("id") Integer id){
			if(id < 197) {
			service.getAgenda(id);
			return new ResponseEntity<>(service.getAgenda(id), HttpStatus.OK);	}
			else {
				service.errore();
				return new ResponseEntity<>(service.errore(), HttpStatus.OK);
			}
			
		}
	@RequestMapping (value= "/statvalue", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getStat(){
		return new ResponseEntity<>(service.getStat(),HttpStatus.OK);
	
		}
	@RequestMapping (value= "/metadata")
	@ResponseBody
	public ResponseEntity<Object> getMetadat(){
		return new ResponseEntity<>(service.getMetadata(), HttpStatus.OK);	
	}
	@RequestMapping (value= "/metadata/{string}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getMetadata(@PathVariable("string") String string){
		if(string.equals("time_period"))
			return new ResponseEntity<>(service.getMetadata().get(0), HttpStatus.OK);
		else if(string.equals("ref_area"))
			return new ResponseEntity<>(service.getMetadata().get(1), HttpStatus.OK);
		else if (string.equals("indicator"))
			return new ResponseEntity<>(service.getMetadata().get(2), HttpStatus.OK);
		else if (string.equals("breakdown"))
			return new ResponseEntity<>(service.getMetadata().get(3), HttpStatus.OK);
		else if(string.equals("unit_measure"))
			return new ResponseEntity<>(service.getMetadata().get(4), HttpStatus.OK);
		else if (string.equals("value"))
			return new ResponseEntity<>(service.getMetadata().get(5), HttpStatus.OK);
		else return null;
			}

	@RequestMapping (value= "/unici/{string}", method = RequestMethod.GET)
	@ResponseBody
		public ResponseEntity<Object> getunici(@PathVariable("string") String string){
			if(string.equals("time_period"))
				return new ResponseEntity<>(service.timeperiodunici(), HttpStatus.OK);
			else if(string.equals("ref_area"))
				return new ResponseEntity<>(service.refareaunici(), HttpStatus.OK);
			else if(string.equals("indicator"))
				return new ResponseEntity<>(service.indicatorunici(), HttpStatus.OK);
			else if(string.equals("breakdown"))
				return new ResponseEntity<>(service.breakdowndunici(), HttpStatus.OK);
			else if(string.equals("unit_measure"))
				return new ResponseEntity<>(service.unitmeasureunici(), HttpStatus.OK);
			else return null;
	
	}
	@RequestMapping (value= "/occorrenze/{string}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getOccorrenze(@PathVariable("string") String string){
		if(string.equals("time_period"))
			return new ResponseEntity<>(service.occorrenzeEstringaTP(), HttpStatus.OK);
		else if(string.equals("ref_area"))
			return new ResponseEntity<>(service.occorrenzeEstringaRA(), HttpStatus.OK);
		else if(string.equals("indicator"))
			return new ResponseEntity<>(service.occorrenzeEstringaI(), HttpStatus.OK);
		else if(string.equals("breakdown"))
			return new ResponseEntity<>(service.occorrenzeEstringaBD(), HttpStatus.OK);
		else if(string.equals("unit_measure"))
			return new ResponseEntity<>(service.occorrenzeEstringaUM(), HttpStatus.OK);
		else return null;}
	 
	
	
		
	}
	
	


