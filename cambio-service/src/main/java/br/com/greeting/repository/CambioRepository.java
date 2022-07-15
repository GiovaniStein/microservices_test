package br.com.greeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.greeting.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long> {
	
	Cambio findByFromAndTo(String from, String to);

}
