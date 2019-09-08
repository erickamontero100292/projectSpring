package com.parameterhttp.services;
import java.util.List;

import com.parameterhttp.model.Profesor;


public interface ProfesorService {
	
	public List<Profesor> getProfesores();
	
	public Profesor getById(int id);
	
	

}
