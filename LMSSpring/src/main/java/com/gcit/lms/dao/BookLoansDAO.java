package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{

	public void addBookLoan(Book book,Borrower borrower, Branch branch) throws SQLException{
		template.update("INSERT INTO tbl_book_loans VALUES (?,?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY ),NULL)", new Object[] {book.getBookId(), branch.getBranchId(),borrower.getCardNo()});
	}
	public int checkoutBook(Integer cardNo, Integer bookID, Integer branchId) {
		try {
			System.out.println(cardNo + " " + bookID + " " + branchId);
			template.update(
					"INSERT INTO tbl_book_loans VALUES (?,?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY ),NULL)",
					new Object[] { bookID, branchId, cardNo });
			template.update(
					"UPDATE tbl_book_copies SET tbl_book_copies.noOfCopies = case when tbl_book_copies.noOfCopies= 0 then tbl_book_copies.noOfCopies else tbl_book_copies.noOfCopies -1 end WHERE tbl_book_copies.bookId = ? and tbl_book_copies.branchId= ?",
					new Object[] { bookID, branchId });
			template.update(
					"UPDATE tbl_book_copies , tbl_book_reserve SET tbl_book_reserve.available = case when tbl_book_copies.noOfCopies = 0 then 0 when tbl_book_copies.noOfCopies > 0  then 1 end WHERE tbl_book_reserve.bookId = ? and tbl_book_reserve.branchId= ? and tbl_book_reserve.bookId= tbl_book_copies.bookId and tbl_book_reserve.branchId= tbl_book_copies.branchId",
					new Object[] { bookID, branchId });
		} catch (Exception e) {
			//e.printStackTrace();
			return -1;
		}
		return 0;
	}
	public List<BookLoans> readLoansOnlyCardNo(Integer cardNo) throws SQLException {
		return template.query("select tbl_book_loans.cardNo,tbl_book.bookId, tbl_book.title, tbl_book_loans.dueDate, tbl_book_loans.dateIn, tbl_library_branch.branchName,tbl_library_branch.branchId from tbl_book, tbl_book_loans,tbl_library_branch where tbl_book_loans.dateIn is null and tbl_book_loans.cardNo=? and tbl_book.bookId= tbl_book_loans.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId",new Object[] {cardNo},this);
	}
	public List<BookLoans> readHistoryOnlyCardNo(Integer cardNo) throws SQLException {
		return template.query("select tbl_book_loans.cardNo,tbl_book.bookId, tbl_book.title, tbl_book_loans.dueDate, tbl_book_loans.dateIn, tbl_library_branch.branchName,tbl_library_branch.branchId from tbl_book, tbl_book_loans,tbl_library_branch where tbl_book_loans.dateIn is not null and tbl_book_loans.cardNo=? and tbl_book.bookId= tbl_book_loans.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId",new Object[] {cardNo},this);
	}
	@Override
	public List<BookLoans> extractData(ResultSet rs) {
		List<BookLoans> bookLoans = new ArrayList<BookLoans>();
		try {
			while(rs.next()){
				BookLoans b = new BookLoans();
				b.setBookId(rs.getInt("bookId"));
				b.setTitle(rs.getString("title"));
				b.setDueDate(rs.getString("dueDate"));
				b.setDateIn(rs.getString("dateIn"));
				b.setBranchName(rs.getString("branchName"));
				b.setCardNo(rs.getInt("cardNo"));
				b.setBranchId(rs.getInt("branchId"));
				bookLoans.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookLoans;
	}

	public void returnBook(Integer cardNo, Integer bookID, Integer branchId) throws SQLException {
		template.update("update tbl_book_loans set dateIn =CURDATE() where cardNo = ? and bookId = ? and branchId=?",
				new Object[] {cardNo,bookID,branchId});
		
		template.update(
				"UPDATE tbl_book_copies SET tbl_book_copies.noOfCopies=tbl_book_copies.noOfCopies + 1  WHERE tbl_book_copies.bookId = ? and tbl_book_copies.branchId= ?",
				new Object[] { bookID, branchId });
		
		template.update("update tbl_book_reserve set tbl_book_reserve.available=1 where tbl_book_reserve.bookId=? and tbl_book_reserve.branchId=?"
				,new Object[] {bookID, branchId});
	}
	public List<BookLoans> readAllLoans() throws SQLException {
		return template.query("select tbl_book_loans.cardNo,tbl_book.bookId, tbl_book.title, tbl_book_loans.dueDate, tbl_book_loans.dateIn, tbl_library_branch.branchName,tbl_library_branch.branchId from tbl_book, tbl_book_loans,tbl_library_branch where tbl_book.bookId= tbl_book_loans.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId",this);
	}
	public void overrideDue(String duedate, Integer cardNo, Integer branchId,
			Integer bookId) throws SQLException {
		System.out.println("bldao:overridedue");
		template.update("update tbl_book_loans set dueDate =? where cardNo = ? and bookId = ? and branchId=?",
				new Object[] {duedate,cardNo,bookId,branchId});
	}

}
