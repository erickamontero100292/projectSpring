package com.viewresolver.services;

import java.util.List;

import com.viewresolver.model.Profesor;



public interface ProfesorService {
	
	public List<Profesor> getProfesores();
	
	public Profesor getById(int id);
	
	

}
