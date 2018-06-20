package hr.tvz.oop.dao;

import hr.tvz.oop.model.User;

import java.util.List;

public interface UserDao {

	/**
	 * Dohvat svih korinika.
	 * 
	 * @return lista korisnika
	 */
	List<User> getUsers();

	/**
	 * Dohvat korisnika po njegovom korisnièkom imenom.
	 * 
	 * @param username
	 * @return korisnik
	 */
	User getUserByUsername(String username);

	/**
	 * Broj iteracija za korisnika.
	 * 
	 * @param userId
	 * @return broj iteracija
	 */
	int getUserIterations(int userId);

}
