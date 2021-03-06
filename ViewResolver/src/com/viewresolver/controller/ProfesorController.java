package com.viewresolver.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viewresolver.services.ProfesorService;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
	
	@Autowired
	private ProfesorService profesorService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String listado(Model model) {
		
		model.addAttribute("profesores", profesorService.getProfesores());
		return "list";
	}
	
	@RequestMapping(value="/otroListado", method = RequestMethod.GET)
	public String otroListado() {
		return "redirect:list";
	}
	
	
	@RequestMapping(value="/detail/{idProf}", method = RequestMethod.GET)
	public String detalle(@PathVariable("idProf") int id, Model model) {
		
		model.addAttribute("profesor", profesorService.getById(id));
		
		return "detail";
	}
	
	
	@RequestMapping(value="/detail/{idProf}/asig/{posAsig}") 
	public String asignatura(@PathVariable("idProf") int idProf, @PathVariable("posAsig") int posAsig, Model model) {	

		model.addAttribute("asignatura", profesorService.getById(idProf).getListadoAsignaturas().get(posAsig));
		
		return "asig";

		
	}
	
	@RequestMapping(value="/detail/{idProf}/asigMap/{posAsig}") 
	public String asignaturamap(@PathVariable Map<String, String> params, Model model) {	

		int idProf = Integer.parseInt(params.get("idProf"));
		int posAsig = Integer.parseInt(params.get("posAsig"));
		
		model.addAttribute("asignatura", profesorService.getById(idProf).getListadoAsignaturas().get(posAsig));
		
		return "asig";

		
	}
	


	public void setProfesorService(ProfesorService profesorService) {
		this.profesorService = profesorService;
	}
	
	
	

}
