package com.proyect.joyeria.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyect.joyeria.dto.PruebDto;


@Service
public class moduloPruebaImpl  implements moduloPrueba{
	
	public Iterable<PruebDto> prueba() {
		// TODO Auto-generated method stub
		List<PruebDto> prueb = new ArrayList<PruebDto>();
		PruebDto pru = new PruebDto();
		for (Integer i =0; i<=5; i++){
			pru.setPassword("pass1");
			pru.setUserId(i);
			pru.setUserName("usuario"+i);
			prueb.add(pru);
		}
		
		return prueb;
	}
	
	

}
