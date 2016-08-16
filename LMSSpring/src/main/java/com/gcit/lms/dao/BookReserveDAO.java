package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.BookReserve;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class BookReserveDAO extends BaseDAO implements ResultSetExtractor<List<BookReserve>>{
	public void addBookLoan(Book book,Borrower borrower, Branch branch) throws SQLException{
		template.update("INSERT INTO tbl_book_reserve VALUES (?,?,?,CURDATE(),0)", new Object[] {book.getTitle(), branch.getBranchId(),borrower.getCardNo()});
	}
	public int reserveBook(Integer cardNo, Integer bookID, Integer branchId) {
		try {
			System.out.println("variables card book branch:"+cardNo + " " + bookID + " " + branchId);
			template.update(
					"INSERT INTO tbl_book_reserve VALUES (?,?,?,CURDATE(),0)",
					new Object[] { bookID, branchId, cardNo });
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public List<BookReserve> viewBookReserve(Integer cardNo) {
		return template.query("select tbl_book_reserve.cardNo,tbl_book_reserve.available ,tbl_book.bookId, tbl_book.title, tbl_book_reserve.dateReserve, tbl_library_branch.branchName,tbl_library_branch.branchId  from tbl_book, tbl_book_reserve,tbl_library_branch  where tbl_book_reserve.cardNo=? and tbl_book.bookId= tbl_book_reserve.bookId  and tbl_book_reserve.branchId= tbl_library_branch.branchId", new Object[] {cardNo},this);
	}
	public void updateReserve(Integer bookId,Integer branchId){
		template.update("update tbl_book_reserve set tbl_book_reserve.available=1 where tbl_book_reserve.bookId=? and tbl_book_reserve.branchId=?",new Object[] {bookId, branchId});
	}
	public Integer getAvailable(int bookId, int branchId){
		int i=0;
		//if ()
		return i;
	}
	
	/*
	 * 
	 * UPDATE tbl_book_copies , tbl_book_reserve
SET tbl_book_reserve.available =
case 
	when tbl_book_copies.noOfCopies=0 then 0 
	else 1
    end
WHERE tbl_book_copies.bookId = 3 and tbl_book_copies.branchId= 7


UPDATE tbl_book_copies , tbl_book_reserve
SET tbl_book_copies.noOfCopies =
case 
	when tbl_book_copies.noOfCopies=0 then tbl_book_copies.noOfCopies - 1  
	else tbl_book_copies.noOfCopies 
    end
WHERE tbl_book_copies.bookId = ? and tbl_book_copies.branchId= ?
	 * 
	 * 
	 * */
	@Override
	public List<BookReserve> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		/**
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private String branchName;
	private String title;
	private String dateReserve;
	private Integer available;
		 * 
		 * */
		List<BookReserve> bookReserves = new ArrayList<BookReserve>();
		try {
			while(rs.next()){
				BookReserve b = new BookReserve();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				b.setDateReserve(rs.getString("dateReserve"));
				//b.setAvailable(getAvailable(rs.getInt("bookId"),rs.getInt("branchId")));
				b.setAvailable(rs.getInt("available"));
				b.setBranchName(rs.getString("branchName"));
				b.setCardNo(rs.getInt("cardNo"));
				b.setBranchId(rs.getInt("branchId"));
				bookReserves.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookReserves;
	}

	
	
}
