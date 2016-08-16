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
import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{

	public Integer addBorrowerWithID(Borrower borrower) throws SQLException {	
		final String name = borrower.getName();
		final String address = borrower.getAddress();
		final String phone = borrower.getPhone();
		final String INSERT_SQL = "insert into tbl_borrower (name,address,phone) values (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "name","address","phone" });
				ps.setString(1, name);
				ps.setString(2, address);
				ps.setString(3, phone);
				return ps;
			}
		}, keyHolder);
		Integer cardNo = keyHolder.getKey().intValue();
		return cardNo;
	}
	public void updateBorrower(Borrower borrower) throws SQLException {
		template.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(),borrower.getPhone(),borrower.getCardNo() });
	}
	
	public void deleteBorrower(Integer borrowerId) throws SQLException {
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {borrowerId});
	}

	/*
	public void addBookBorrower(Borrower borrower) throws SQLException {
		for(Book b: borrower.getBookLoans()){
			save("insert into tbl_book_loans (bookId, authorId) values (?, ?)", new Object[] { author.getAuthorID(), b.getBookId() });
		}
	}
	*/
	@Override
	public List<Borrower> extractData(ResultSet rs) {
		List<Borrower> borrower = new ArrayList<Borrower>();
		try {
			while (rs.next()) {
				Borrower a = new Borrower();
				a.setCardNo(rs.getInt("cardNo"));
				a.setName(rs.getString("name"));
				a.setAddress(rs.getString("address"));
				a.setPhone(rs.getString("phone"));
//				a.setBooks(bdao.readFirstLevel(
//						"select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?))",
//						new Object[] { a.getAuthorID()}));
				borrower.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrower;
	}

	public List<Borrower> readAllBorrowers(int pageNo) throws SQLException {
		setPageNo(pageNo);
		return template.query("select * from tbl_borrower", this);
	}
	public Borrower readBorrowerByID(int cardNo) throws SQLException {
		List<Borrower> borrowers = template.query("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo},this);
		if(borrowers.size()!=0){
			return borrowers.get(0);
		}
		return null;
	}

	public void addBorrower(Borrower borrower) throws SQLException {
		template.update("insert into tbl_borrower (name,phone,address) values (?,?,?)", new Object[] { borrower.getName(),borrower.getPhone(),borrower.getAddress() });
		}

}
