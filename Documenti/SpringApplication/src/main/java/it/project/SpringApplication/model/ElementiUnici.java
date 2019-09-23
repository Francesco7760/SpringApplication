package it.project.SpringApplication.model;

public class ElementiUnici {
	String nome ;
	int occorrenze ;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getOccorrenze() {
		return occorrenze;
	}

	public void setOccorrenze(int occorrenze) {
		this.occorrenze = occorrenze;
	}

	public ElementiUnici(String nome, int occorrenze) {
		this.nome=nome;
		this.occorrenze=occorrenze;
	}
	

}
