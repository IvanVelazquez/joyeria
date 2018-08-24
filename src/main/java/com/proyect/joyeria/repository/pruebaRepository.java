package com.proyect.joyeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyect.joyeria.dto.PruebDto;
import com.proyect.joyeria.entities.*;

public interface pruebaRepository extends CrudRepository<prueba,Integer>{

	@Query(value="select new com.proyect.joyeria.dto.PruebDto(p.userId,p.userName,p.password) from prueba p")
	List<PruebDto> findByPrueba();
}
