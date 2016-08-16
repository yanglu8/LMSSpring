package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{
	public void addGenre(Genre genre) throws SQLException{
		template.update("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	public Integer addGenreWithID(Genre genre) throws SQLException {
		final String genreName = genre.getGenreName();
		final String INSERT_SQL = "insert into tbl_genre (genre_name) values (?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "genreId" });
				ps.setString(1, genreName);
				return ps;
			}
		}, keyHolder);
		Integer genreId = keyHolder.getKey().intValue();
		return genreId;
	}
	
	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> genres = new ArrayList<Genre>();
		try {
			while (rs.next()) {
				Genre g = new Genre();
				g.setGenreId(rs.getInt("genre_id"));
				g.setGenreName(rs.getString("genre_name"));
				genres.add(g);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genres;
	}

	public List<Genre> readAllGenres(int pageNo) throws SQLException {
		setPageNo(pageNo);
		List<Genre> genres = template.query("select * from tbl_genre", this);
		return genres;
	}
	public List<Genre> readGenreByBook(int pageNo, Integer bookId) {
		setPageNo(pageNo);
		return template.query("select * from tbl_genre, tbl_book_genres where tbl_book_genres.bookId= ? and tbl_genre.genre_id = tbl_book_genres.genre_id ", new Object[] {bookId}, this);
	}
	
	
}
