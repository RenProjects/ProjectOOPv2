package hr.tvz.oop.model;

/**
 * Korisnik.
 */
public class User {
	
	public static final String USER_IN_SESSION = "oopUser";
	public static final String USER_QUIZ_ID = "oopUserQuizId";

	private Integer id;
	private String username;
	private Integer roleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
