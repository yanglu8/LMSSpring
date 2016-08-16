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
import com.gcit.lms.entity.Book;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{

	public void addAuthor(Author author) throws SQLException {
		template.update("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public Integer addAuthorWithID(Author author) {
		final String authorName = author.getAuthorName();
		final String INSERT_SQL = "insert into tbl_author (authorName) values (?) ";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "authorId" });
				ps.setString(1, authorName);
				return ps;
			}
		}, keyHolder);
		Integer authorId = keyHolder.getKey().intValue();
		return authorId;
	}
	
	
	public void addBookAuthor(Author author, Integer authorId) throws SQLException {
		System.out.println("authorId: "+authorId);
		for(Book b: author.getBooks()){
			System.out.println("BookId: "+b.getBookId());
			template.update("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[] {b.getBookId(),authorId});
		}
	}

	public void updateAuthor(Author author) throws SQLException {
		template.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorID() });
	}
	public void deleteBookAuthor(Author author){
		template.update("delete from tbl_book_authors where authorId = ?", new Object[] { author.getAuthorID() });
	}
	public void deleteAuthor(Integer authorId) throws SQLException {
		template.update("delete from tbl_author where authorId = ?", new Object[] {authorId});
	}

	public List<Author> readAllAuthors(int pageNo) throws SQLException {
		setPageNo(pageNo);
		return template.query("select * from tbl_author", this);
	}

	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();
		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorID(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				authors.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	public Author readByID(Integer authorID) throws SQLException {
		List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[] {authorID}, this);
		if(authors!=null){
			return authors.get(0);
		}
		return null;
	}


	public List<Author> readAuthorsByName(String searchString, Integer pageNo) throws SQLException {
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return template.query("select * from tbl_author where authorName like ?", new Object[] {searchString}, this);
	}

	public List<Author> readAuthorByBook(int pageNo, Integer bookId) {
		setPageNo(pageNo);
		return template.query("select * from tbl_author, tbl_book_authors where tbl_book_authors.bookId = ? and tbl_book_authors.authorId = tbl_author.authorId", new Object[] {bookId}, this);
	}
	
}
