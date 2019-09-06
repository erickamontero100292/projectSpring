package  com.mapeourl.services;

import java.util.List;

import com.mapeourl.model.Profesor;


public interface ProfesorService {
	
	public List<Profesor> getProfesores();
	
	public Profesor getById(int id);
	
	

}
