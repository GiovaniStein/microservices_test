package br.com.greeting.proxy;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.greeting.dto.CambioDto;

@FeignClient(name = "cambio-service")
public interface CambioProxy {

	@GetMapping("/cambio-service/{amount}/{from}/{to}")
	public ResponseEntity<CambioDto> getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from,
			@PathVariable("to") String to);

}
