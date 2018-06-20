package hr.tvz.oop.dao.jdbc;

import java.sql.Timestamp;
import java.sql.Types;

import javax.sql.DataSource;

import hr.tvz.oop.dao.ResultsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ResultsDaoJdbc extends NamedParameterJdbcDaoSupport implements
		ResultsDao {

	@Value("INSERT INTO test_results (user_id, test_start) VALUES (:userId, :startTime)")
	String initializeQuery;

	@Value("INSERT INTO test_result_single (image_id, results_id, is_correct) VALUES ( :imageId, :resultsId, :isCorrect )")
	String insertResultQuery;

	@Value("UPDATE test_results SET test_finish = :finishTime WHERE id = :quizId AND user_id = :userId")
	String finishQuery;

	@Autowired
	public ResultsDaoJdbc(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.ResultsDao#initializeQuiz(int)
	 */
	@Override
	public int initializeQuiz(int userId) {
		KeyHolder holder = new GeneratedKeyHolder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("userId", Integer.valueOf(userId), Types.INTEGER);
		paramSource.addValue("startTime",
				new Timestamp(System.currentTimeMillis()), Types.TIMESTAMP);

		getNamedParameterJdbcTemplate().update(initializeQuery, paramSource,
				holder);
		int key = holder.getKey().intValue();
		return key;
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.ResultsDao#saveResult(int, int, boolean)
	 */
	@Override
	public void saveResult(int imageId, int resultsId, boolean isCorrect) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("imageId", imageId, Types.INTEGER);
		paramSource.addValue("resultsId", resultsId, Types.INTEGER);
		paramSource.addValue("isCorrect", isCorrect, Types.TINYINT);

		getNamedParameterJdbcTemplate().update(insertResultQuery, paramSource);
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.ResultsDao#finishQuiz(int, int)
	 */
	@Override
	public void finishQuiz(int userId, int quizId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("userId", userId, Types.INTEGER);
		paramSource.addValue("quizId", quizId, Types.INTEGER);
		paramSource.addValue("finishTime",
				new Timestamp(System.currentTimeMillis()), Types.TIMESTAMP);

		getNamedParameterJdbcTemplate().update(finishQuery, paramSource);
	}

}
