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
import com.gcit.lms.entity.Genre;

@SuppressWarnings("unchecked")
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{
	

	public void addBook(Book book) throws SQLException{
		template.update("insert into tbl_book (title, pubId) values (?, ?)", new Object[] {book.getTitle(), book.getPid()});
	}
	
	public Integer addBookWithID(Book book) {
		final String title = book.getTitle();
		final Integer pid = book.getPid();
		final String INSERT_SQL = "insert into tbl_book (title, pubId) values (?, ?) ";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "bookId" });
				ps.setString(1, title);
				ps.setInt(2, pid);
				return ps;
			}
		}, keyHolder);
		Integer bookId = keyHolder.getKey().intValue();
		System.out.println("BOOKID£º "+bookId);
		return bookId;
	}
	
	public void updateBook(Book book) throws SQLException{
		//template.update("update tbl_book set title = ? where bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
		template.update("update tbl_book set title = ?,pubId=? where bookId = ?", new Object[] {book.getTitle(),book.getPid(), book.getBookId()});
		template.update("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()});
		int i =0;
		while(i<book.getAuthors().size()){
			template.update("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[] {book.getBookId(), book.getAuthors().get(i).getAuthorID()});
			i++;
		}
		template.update("delete from tbl_book_genres where bookId = ?", new Object[] {book.getBookId()});

		i=0;
		while(i<book.getGenres().size()){
			template.update("insert into tbl_book_genres (genre_id, bookId) values (?, ?)", new Object[] {book.getGenres().get(i).getGenreId(),book.getBookId()} );
			i++;
		}
	
	
	}
	public void addAuthorBook(Book book){
		for(Author a: book.getAuthors()){
			template.update("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[] {book.getBookId(), a.getAuthorID() });
		}
	}
	public void addGenreBook(Book book){
		for(Genre g: book.getGenres()){
			template.update("insert into tbl_book_genres (genre_id, bookId) values (?, ?)", new Object[] {g.getGenreId(),book.getBookId()} );		}
	}
	public void deleteBook(Integer bookId) throws SQLException{
		template.update("delete from tbl_book where bookId = ?", new Object[] {bookId});
	}
	public void deleteGenreBook(Book book) throws SQLException{
		for(Genre g: book.getGenres()){
			template.update("delete from tbl_book_genres where bookId = ?", new Object[] {book.getBookId()} );		}
	}
	public void deleteAuthorBook(Book book) throws SQLException{
		for(Genre g: book.getGenres()){
			template.update("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()} );		}
	}
	public List<Book> readBookExceptCardNo(Integer cardNo) throws SQLException{
		return template.query("select tbl_book_copies.bookId, title, tbl_book_copies.branchId, tbl_library_branch.branchName from tbl_book,tbl_book_copies,tbl_library_branch where tbl_book_copies.bookId not in (select tbl_book_loans.bookId from tbl_book_loans where tbl_book_loans.cardNo= ?) and tbl_book.bookId= tbl_book_copies.bookId and tbl_book_copies.branchId= tbl_library_branch.branchId", new Object[] {cardNo},this);
	}
	public List<Book> readBookOnlyCardNo(Integer cardNo) throws SQLException{
		return template.query("select distinct tbl_book_copies.bookId,title, tbl_book_copies.branchId, tbl_library_branch.branchName from tbl_book,tbl_book_copies,tbl_library_branch where tbl_book_copies.bookId in (select tbl_book_loans.bookId from tbl_book_loans where tbl_book_loans.cardNo= ?) and tbl_book.bookId= tbl_book_copies.bookId and tbl_book_copies.branchId= tbl_library_branch.branchId", new Object[] {cardNo},this);
	}
	public Integer getBookCount() throws SQLException {
		return  template.queryForObject(
				"select count(*) from tbl_book", Integer.class);
	}
	public List<Book> readAllBooks() throws SQLException{
		List<Book> Book = template.query("select * from tbl_book", this);
		return Book;
	}

	@Override
	public List<Book> extractData(ResultSet rs){
		List<Book> books = new ArrayList<Book>();
		try {
			while(rs.next()){
				Book b = new Book();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				b.setPid(rs.getInt("pubId"));
				//add genres & publisher
				books.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	public List<Book> readBookByAuthor(int pageNo, int authorId) {
		return template.query("select * from tbl_book, tbl_book_authors where tbl_book.bookId=tbl_book_authors.bookId and tbl_book_authors.authorId= ?", new Object[] {authorId},this);
	}

	public Object readBorrowerByID(Integer cardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Book> readBookById(Integer bookId) {
		// TODO Auto-generated method stub
		return template.query("select * from tbl_book where bookId = ?", new Object[] {bookId}, this);
	}

}
