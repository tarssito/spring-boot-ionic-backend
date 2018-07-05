package com.tarssito.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarssito.cursomc.domain.Estado;
import com.tarssito.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> findAll() {
		return repository.findAllByOrderByNome();
	}
}
