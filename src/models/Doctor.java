package models;

import java.sql.Date;

public class Doctor {
	int id;
	String nomprenom;
	Date dateNaiss;
	String tele;
	String cin;
	
	public Doctor() {
		super();
	}
	
	public Doctor (int id, String nom, Date date, String tele, String cin) {
		this.id = id;
		this.nomprenom = nom;
		this.dateNaiss = date;
		this.tele = tele;
		this.cin = cin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomprenom() {
		return nomprenom;
	}

	public void setNomprenom(String nomprenom) {
		this.nomprenom = nomprenom;
	}

	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}
}
