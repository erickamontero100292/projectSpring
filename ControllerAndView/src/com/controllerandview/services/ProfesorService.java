package  com.controllerandview.services;

import java.util.List;

import  com.controllerandview.model.Profesor;


public interface ProfesorService {
	
	public List<Profesor> getProfesores();
	
	public Profesor getById(int id);
	
	

}
