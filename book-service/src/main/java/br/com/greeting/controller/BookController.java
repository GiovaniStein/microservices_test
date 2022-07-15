package br.com.greeting.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.greeting.dto.CambioDto;
import br.com.greeting.model.Book;
import br.com.greeting.proxy.CambioProxy;
import br.com.greeting.service.BookService;

@RestController
@RequestMapping("/book-service")
public class BookController {

	@Autowired
	private Environment environment;
	@Autowired
	private BookService bookService;
	@Autowired
	private CambioProxy cambioProxy;

	@GetMapping
	public ResponseEntity<List<Book>> findAll() {
		return ResponseEntity.ok(bookService.findAll());
	}

	@GetMapping(value = "/{id}/{currency}")
	public ResponseEntity<Book> findBook(@PathVariable(name = "id") Long id,
			@PathVariable(name = "currency") String currency) {

		try {
			Book book = bookService.findById(id);

			if (book != null) {
				String port = environment.getProperty("local.server.port");

				ResponseEntity<CambioDto> response = cambioProxy.getCambio(book.getPrice(), "USD", currency);

				if (response.getStatusCode() == HttpStatus.OK) {
					CambioDto cambio = response.getBody();
					book.setEnvironment(String.format("Book port: %s Cambio port: %s", port, cambio.getEnvironment()));
					book.setPrice(cambio.getConvertedValue());
				}

				return ResponseEntity.ok(book);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return null;

	}

	/**
	 * Old version
	 * 
	 * @GetMapping(value = "/{id}/{currency}") public ResponseEntity<Book>
	 *                   findBook(@PathVariable(name = "id") Long id,
	 * @PathVariable(name = "currency") String currency) {
	 * 
	 *                    try { Book book = bookService.findById(id);
	 * 
	 *                    if (book != null) { String port =
	 *                    environment.getProperty("local.server.port");
	 *                    book.setEnvironment(port);
	 * 
	 *                    HashMap<String, String> params = new HashMap<>();
	 *                    params.put("amount", book.getPrice().toString());
	 *                    params.put("from", "USD"); params.put("to", currency);
	 * 
	 *                    ResponseEntity<CambioDto> response = new
	 *                    RestTemplate().getForEntity(
	 *                    "http://localhost:8000/cambio-service/{amount}/{from}/{to}",
	 *                    CambioDto.class, params);
	 * 
	 *                    if (response.getStatusCode() == HttpStatus.OK) { CambioDto
	 *                    cambio = response.getBody();
	 *                    book.setPrice(cambio.getConvertedValue()); }
	 * 
	 *                    return ResponseEntity.ok(book); }
	 * 
	 *                    } catch (Exception e) { throw new RuntimeException(e); }
	 * 
	 *                    return null;
	 * 
	 *                    }
	 **/

}
