package br.com.greeting.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = -3944855797880986081L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "author", nullable = false, length = 180)
	private String author;
	@Column(name = "launch_date", nullable = false)
	private LocalDateTime launchDate;
	@Column(nullable = false)
	private BigDecimal price;
	@Column(nullable = false, length = 250)
	private String title;
	@Transient
	private String currency;
	@Transient
	private String environment;

}
