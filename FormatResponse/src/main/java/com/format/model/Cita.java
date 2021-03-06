package com.format.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cita {
	
	private Long id;
	private String texto;
	private Date fecha;
	
	public Cita() { }

	public Cita(Long id, String texto, Date fecha) {
		this.id = id;
		this.texto = texto;
		this.fecha = fecha;
	}

	@XmlElement
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@XmlElement
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
	

}
