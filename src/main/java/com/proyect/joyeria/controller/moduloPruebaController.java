package com.proyect.joyeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.joyeria.dto.PruebDto;
import com.proyect.joyeria.services.moduloPrueba;

@RestController
public class moduloPruebaController {
	@Autowired
	private moduloPrueba modPrueba;
	
	
	@RequestMapping(value="/prueba",method=RequestMethod.GET)
	public @ResponseBody Iterable<PruebDto> prueba(){
		
		return modPrueba.prueba();
		
	}

}
