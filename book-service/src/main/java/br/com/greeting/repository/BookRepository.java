package br.com.greeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.greeting.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
