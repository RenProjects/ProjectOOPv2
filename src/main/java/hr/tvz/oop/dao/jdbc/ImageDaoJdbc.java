package hr.tvz.oop.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import hr.tvz.oop.dao.ImageDao;
import hr.tvz.oop.model.Category;
import hr.tvz.oop.model.Image;

@Component
public class ImageDaoJdbc extends NamedParameterJdbcDaoSupport implements
		ImageDao {

	/**
	 * SELECT * FROM images WHERE images.category=0 ORDER BY RAND() LIMIT 0,1;
	 */
	@Value("SELECT * FROM images " + "WHERE images.category=:category "
			+ "ORDER BY RAND() LIMIT 0,1;")
	private String queryRandom;

	@Autowired
	public ImageDaoJdbc(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/* (non-Javadoc)
	 * @see hr.tvz.oop.dao.ImageDao#getRandomImages()
	 */
	@Override
	public List<Image> getRandomImages() {
		// Get image for positive category
		Image positiveImage = (Image) getNamedParameterJdbcTemplate()
				.queryForObject(
						queryRandom,
						new MapSqlParameterSource().addValue("category",
								Category.POSITIVE.getValue(), Types.TINYINT),
						new ImageRowMapper());
		positiveImage.setCategory(Category.POSITIVE);
		
		// Get image for negative category
		Image negativeImage = (Image) getNamedParameterJdbcTemplate()
				.queryForObject(
						queryRandom,
						new MapSqlParameterSource().addValue("category",
								Category.NEGATIVE.getValue(), Types.TINYINT),
						new ImageRowMapper());
		negativeImage.setCategory(Category.NEGATIVE);
		
		List<Image> images = new ArrayList<Image>();
		images.add(positiveImage);
		images.add(negativeImage);

		return images;
	}

	/**
	 * Klasa za mapiranje objekta {@link Image}
	 */
	private class ImageRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Image image = new Image();
			image.setId(rs.getInt("id"));
			image.setUrl(rs.getString("image_url"));
			return image;
		}

	}

}
