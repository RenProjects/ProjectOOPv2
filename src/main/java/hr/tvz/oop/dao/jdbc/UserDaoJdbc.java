package hr.tvz.oop.dao.jdbc;

import hr.tvz.oop.dao.UserDao;
import hr.tvz.oop.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class UserDaoJdbc extends NamedParameterJdbcDaoSupport implements
		UserDao {

	@Value("SELECT * FROM users")
	private String queryUsers;

	@Value("SELECT * FROM users WHERE users.username = :username")
	private String queryUser;

	@Value("SELECT user_properties.value FROM user_properties WHERE user_properties.user_id = :userId")
	private String queryUserIterations;

	@Autowired
	public UserDaoJdbc(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.UserDao#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		@SuppressWarnings("unchecked")
		List<User> list = getJdbcTemplate().query(queryUsers,
				new UserRowMapper());
		return list;
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.UserDao#getUserByUsername(java.lang.String)
	 */
	@Override
	public User getUserByUsername(String username) {
		User user = (User) getNamedParameterJdbcTemplate().queryForObject(
				queryUser,
				new MapSqlParameterSource().addValue("username", username,
						Types.VARCHAR), new UserRowMapper());
		return user;
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.UserDao#getUserIterations(int)
	 */
	@Override
	public int getUserIterations(int userId) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userId", Integer.valueOf(userId));
		Integer iterations = (Integer) getNamedParameterJdbcTemplate().queryForInt(queryUserIterations, paramMap);
		return iterations;
	}

	/**
	 *  Klasa za mapiranje objekta {@link User}
	 */
	private class UserRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			return user;
		}

	}

}
