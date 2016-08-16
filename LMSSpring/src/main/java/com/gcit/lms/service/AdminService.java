package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.BaseDAO;
import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.mysql.jdbc.Blob;

public class AdminService {
	
	@Autowired
	AuthorDAO adao;
	@Autowired
	BookDAO bdao;
	@Autowired
	GenreDAO gdao;
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BranchDAO brdao;
	@Autowired
	BorrowerDAO bodao;
	@Autowired
	PublisherDAO pdao;
	@Autowired
	BookLoansDAO bldao;
	@Transactional
	public void addAuthor(Author author) throws SQLException{
		Integer authorId = adao.addAuthorWithID(author);
		author.setAuthorID(authorId);
		adao.addBookAuthor(author,authorId);
	}
	@Transactional
	public void deleteAuthor(Integer authorId) throws SQLException{
		adao.deleteAuthor(authorId);
	}
	@Transactional
	public void updateAuthor(Author author) throws SQLException{
		adao.updateAuthor(author);
		adao.deleteBookAuthor(author);
		adao.addBookAuthor(author, author.getAuthorID());
	}
	
	@Transactional
	public void addBook(Book book) throws SQLException{
		Integer bookId = bdao.addBookWithID(book);
		book.setBookId(bookId);
		bdao.addAuthorBook(book);
		bdao.addGenreBook(book);
	}
	@Transactional
	public void deleteBook(Integer bookId) throws SQLException{
		bdao.deleteBook(bookId);
	}
	@Transactional
	public void updateBook(Book book) throws SQLException{
		bdao.updateBook(book);
		bdao.deleteAuthorBook(book);
		bdao.addAuthorBook(book);
		bdao.deleteGenreBook(book);
		bdao.addGenreBook(book);
	}
	
	
	@Transactional
	public void addBranch(Branch branch) throws SQLException{
		brdao.addBranch(branch);
	}
	@Transactional
	public void addPublisher(Publisher publisher) throws SQLException {
		pdao.addPublisher(publisher);
		
	}

	@Transactional
	public void addBorrower(Borrower borrower) throws SQLException{
		bodao.addBorrower(borrower);
		//add book list
	}
	@Transactional
	public void deleteBranch(Integer branchId) throws SQLException{
		brdao.deleteBranch(branchId);
	}
	@Transactional
	public void deleteBorrower(Integer cardNo) throws SQLException{
		bodao.deleteBorrower(cardNo);
	}
	@Transactional
	public void deletePublisher(Integer publisherId) throws SQLException {
		pdao.deletePublisher(publisherId);
		
	}
	@Transactional
	public void updateBranch(Branch branch) throws SQLException{
		brdao.updateBranch(branch);
	}
	public void updateCopies(BookCopies bookCopies) throws SQLException {
		bcdao.updateBookCopies(bookCopies);
		// TODO Auto-generated method stub
		
	}
	@Transactional
	public List<BookCopies> readBranchById(Integer branchId) throws SQLException{
		return bcdao.readBranchById(branchId);
	}
	@Transactional
	public List<Branch> viewBranches(Integer pageNo) throws SQLException{
		System.out.println("viewBranches");
		if (brdao ==null){
			System.out.println("brdaonull");
		}
		List<Branch> branches = brdao.readAllBranches(pageNo);
		System.out.println("branches size:"+branches.size());
		for (Branch b:branches){
			b.setCopyList(this.readBranchById(b.getBranchId()));
		}
		System.out.println("return");
		return branches;
	}
	@Transactional
	public Branch viewBranchByID(Integer branchId) throws SQLException{
		System.out.println("viewBranches");
		if (brdao ==null){
			System.out.println("brdaonull");
		}
		Branch branch = brdao.readBorrowerByID(branchId);
		branch.setCopyList(this.readBranchById(branch.getBranchId()));
		System.out.println("return");
		return branch;
	}
	
	@Transactional
	public Borrower viewBorrowerByID(Integer cardNo) throws SQLException{
		System.out.println("viewBorrowerByID");
		if (bodao ==null){
			System.out.println("brdaonull");
		}
		Borrower borrower = bodao.readBorrowerByID(cardNo);
		System.out.println("return");
		return borrower;
	}
	
	@Transactional
	public List<Author> viewAuthors(int pageNo) throws SQLException{
		List<Author> authors = adao.readAllAuthors(pageNo);
		for (Author a: authors){
			a.setBooks(this.viewBooksByAuthor(pageNo,a.getAuthorID()));
		}
		return authors;
	}
	public List<Publisher> viewPublishers(int pageNo) throws SQLException{
		List<Publisher> publishers = pdao.readAllPublishers(pageNo);
		for (Publisher p: publishers){
			//p.setBooks(this.viewBooksByAuthor(pageNo,a.getAuthorID()));
		}
		return publishers;
	}
	@Transactional
	public List<Book> viewBooks(int pageNo) throws SQLException{
		List<Book> books = bdao.readAllBooks();
		for (Book b: books){
			b.setAuthors(adao.readAuthorByBook(pageNo, b.getBookId()));
			b.setGenres(gdao.readGenreByBook(pageNo,b.getBookId()));
			//b.setPid(pid);
		}
		return books;
	}
	@Transactional
	public List<Borrower> viewBorrowers(int pageNo) throws SQLException{
		List<Borrower> borrowers = bodao.readAllBorrowers(pageNo);
		for (Borrower b: borrowers){
			b.setBookLoans(bldao.readLoansOnlyCardNo(b.getCardNo()));
		}
		return borrowers;
	}
	@Transactional
	public List<Book> viewBooksByAuthor(int pageNo, int authorId) throws SQLException{
		return bdao.readBookByAuthor(pageNo,authorId);
	}
	@Transactional
	public List<Genre> viewGenres(int pageNo) throws SQLException{
		return gdao.readAllGenres(pageNo);
	}
//	@Transactional
//	public List<BookCopies> viewBookCopies(int branchId) throws SQLException{
//		return bcdao.
//	}
	@Transactional
	public Author viewAuthorByID(Integer authorID) throws SQLException{
		Author author = adao.readByID(authorID);
		author.setBooks(bdao.readBookByAuthor(1, authorID));
		return author;
	}
	@Transactional
	public Book viewBookByID(Integer bookId) throws SQLException{
		Book book= bdao.readBookById(bookId).get(0);
		book.setAuthors(adao.readAuthorByBook(1, bookId));
		book.setGenres(gdao.readGenreByBook(1, bookId));
		return book;
	}
	@Transactional
	public void editAuthor(Author author) throws SQLException {
		adao.updateAuthor(author);
		adao.deleteBookAuthor(author);
		if(author.getBooks()!=null){
		adao.addBookAuthor(author, author.getAuthorID());}
	}
	@Transactional
	public void editBook(Book book) {
		try {
			bdao.updateBook(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void editBorrower(Borrower borrower) {
		// TODO Auto-generated method stub
		try {
			bodao.updateBorrower(borrower);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	public Integer getAuthorCount(String searchString) throws SQLException{
//		Connection conn =  ConnectionUtil.getConnection();
//		try {
//			AuthorDAO adao = new AuthorDAO();
//			if(searchString!=null && !("").equals(searchString)){
//				return adao.getAuthorCountByName(searchString);
//			}else{
//				return adao.getAuthorCount();
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally{
//			conn.close();
//			
//		}
//		return null;
//	}
//	
//	public List<Author> readAuthorsByName(String searchString, Integer pageNo) throws SQLException{
//		Connection conn =  ConnectionUtil.getConnection();
//		try {
//			AuthorDAO adao = new AuthorDAO(conn);
//			if(searchString!=null && !("").equals(searchString)){
//				return adao.readAuthorsByName(searchString, pageNo);
//			}else{
//				return adao.readAllAuthors(pageNo);
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally{
//			conn.close();
//		}
//		return null;
//	}



}
