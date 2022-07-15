package br.com.greeting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.greeting.model.Cambio;
import br.com.greeting.repository.CambioRepository;

@Service
public class CambioService {

	@Autowired
	private CambioRepository repository;
	
	public List<Cambio> findAll() {
		return repository.findAll();
	}
	
	public Cambio findByFromAndTo(String from, String to) {
		return repository.findByFromAndTo(from, to);
	}
	
}
