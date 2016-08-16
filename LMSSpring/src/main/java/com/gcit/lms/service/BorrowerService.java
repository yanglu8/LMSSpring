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
import com.gcit.lms.dao.BookReserveDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.BookReserve;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.mysql.jdbc.Blob;
public class BorrowerService {
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
	@Autowired
	BookReserveDAO bkrDao;
	@Transactional
	public List<BookLoans> viewAllLoans() throws SQLException{
		System.out.println("viewAllLoans*****");
		return bldao.readAllLoans();
	}
	@Transactional
	public void updateDueDate(String duedate, Integer cardNo, Integer branchId,
			Integer bookId) throws SQLException {
			System.out.println("updatedue");
			if(bldao==null){
				System.out.println("NULL!@@@@");
				
			}
			bldao.overrideDue(duedate,cardNo, branchId,bookId);
		
	}
	@Transactional
	public List<BookCopies> viewBooks(Integer cardNo) throws SQLException {
		return bcdao.readBookOnlyCardNo(cardNo);
	}
	@Transactional
	public List<BookLoans> viewBookLoans(Integer cardNo) throws SQLException {
		return bldao.readLoansOnlyCardNo(cardNo);
	}
	@Transactional
	public List<BookLoans> viewHistoryLoans(Integer cardNo) throws SQLException {
		return bldao.readHistoryOnlyCardNo(cardNo);
	}
	@Transactional
	public List<BookReserve> viewBookReserve(Integer cardNo) throws SQLException {
		return bkrDao.viewBookReserve(cardNo);
	}
	/*
	public void checkBook(Book book,Branch branch){		
	}
	
	public boolean checkCardNo(int cardNo) throws SQLException{
		Connection conn = ConnectionUtil.getConnection();
		boolean valid = false;
		try{
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(bdao.readBorrowerByID(cardNo) != null)
				valid = true;
			
		}catch (Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return valid;
	}
	public void returnBook(Integer cardNo,Integer bookID,Integer branchId) throws SQLException{
		System.out.println("service return");
		Connection conn = ConnectionUtil.getConnection();
		try {
			BookLoansDAO bdao = new BookLoansDAO(conn);
			bdao.returnBook(cardNo,bookID,branchId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	public boolean varifyCard(Integer cardNo) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		boolean valid = false;
		try{
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(bdao.readBorrowerByID(cardNo)!=null){
				valid = true;
				System.out.println("service"+cardNo);
			}
			else {
				System.out.println("servicefail"+cardNo);
			}
		}catch (Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return valid;
	}
	public List<BookCopies> viewBooks(Integer cardNo) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		try {
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			return bdao.readBookOnlyCardNo(cardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	public List<BookLoans> viewBookLoans(Integer cardNo) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		try {
			BookLoansDAO bdao = new BookLoansDAO(conn);
			return bdao.readLoansOnlyCardNo(cardNo);
		} finally {
			conn.close();
		}
	}
	public List<BookLoans> viewAllLoans() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();
		try {
			BookLoansDAO bdao = new BookLoansDAO(conn);
			return bdao.readAllLoans();
		} finally {
			conn.close();
		}
	}
	public void checkoutBook(Integer cardNo,Integer bookID,Integer branchId) throws SQLException {
		System.out.println("service borrow");
		Connection conn = ConnectionUtil.getConnection();
		try {
			BookLoansDAO bdao = new BookLoansDAO(conn);
			bdao.checkoutBook(cardNo,bookID,branchId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	public void updateCopies(BookCopies bookCopies){}
	*/
	public boolean varifyCard(Integer cardNo) throws SQLException {
		// TODO Auto-generated method stub
		boolean valid = false;
		if(bodao.readBorrowerByID(cardNo)!=null){
			valid = true;
			System.out.println("service"+cardNo);
		}
		else {
			System.out.println("servicefail"+cardNo);
		}
		return valid;
	}
	public int checkoutBook(Integer cardNo, Integer bookId, Integer branchId){
		int i =0;
		try {
			i =bldao.checkoutBook(cardNo, bookId, branchId);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("checkoutBook errors!!!"+i);
		}
		return i;
	}
	@Transactional
	public void returnBook(Integer cardNo, Integer bookID, Integer branchId) throws SQLException {
		bldao.returnBook(cardNo,bookID,branchId);
		
	}
	@Transactional
	public int reserveBook(Integer cardNo, Integer bookId, Integer branchId) {
		System.out.println("variables card book branch:"+cardNo + " " + bookId + " " + branchId);
		int i =0;
		try {
			i =bkrDao.reserveBook(cardNo, bookId, branchId);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("checkoutBook errors!!!"+i);
		}
		return i;
	}
}
