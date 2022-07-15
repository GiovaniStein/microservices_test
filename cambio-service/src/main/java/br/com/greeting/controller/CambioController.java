package br.com.greeting.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.greeting.model.Cambio;
import br.com.greeting.service.CambioService;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

	@Autowired
	private Environment environment;
	@Autowired
	private CambioService cambioService;

	@GetMapping("/{amount}/{from}/{to}")
	private ResponseEntity<Cambio> getCambio(@PathVariable("amount") BigDecimal amount,
			@PathVariable("from") String from, @PathVariable("to") String to) {

		String port = environment.getProperty("local.server.port");

		Cambio cambio = cambioService.findByFromAndTo(from, to);

		if (cambio != null) {
			cambio.setEnvironment(port);
			cambio.setConvertedValue(cambio.getConversionFactor().multiply(amount).setScale(2, RoundingMode.CEILING));

			return ResponseEntity.ok(cambio);
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<Cambio>> getCambio() {
		return ResponseEntity.ok(cambioService.findAll());
	}

}
