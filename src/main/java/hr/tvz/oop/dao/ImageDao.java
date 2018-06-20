package hr.tvz.oop.dao;

import hr.tvz.oop.model.Image;

import java.util.List;

public interface ImageDao {
	
	/**
	 * Metoda za dohvat slika iz baze podataka.
	 * 
	 * @return lista slika
	 */
	List<Image> getRandomImages();

}
