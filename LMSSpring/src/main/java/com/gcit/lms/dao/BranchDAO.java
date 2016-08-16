package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>>{
	public List<Branch> readAllBranches(int pageNo) throws SQLException {
		System.out.println("readAllBranches");
		setPageNo(pageNo);
		return template.query("select * from tbl_library_branch", this);
	}
	public Integer addBranchWithID(Branch branch) {
		final String branchName = branch.getBranchName();
		final String INSERT_SQL = "insert into tbl_library_branch (branchName,branchAddress) values (?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "branchId" });
				ps.setString(1, branchName);
				return ps;
			}
		}, keyHolder);
		Integer branchId = keyHolder.getKey().intValue();
		return branchId;
	}
	
	public void updateBranch(Branch branch) throws SQLException{
		template.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(),branch.getBranchId() });
	}
	public void deleteAuthor(Branch branch) throws SQLException {
		template.update("delete from tbl_library_branch where branchId = ?", new Object[] { branch.getBranchId()});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> extractData(ResultSet rs) {
		System.out.println("extractData");
		
		List<Branch> branches = new ArrayList<Branch>();
		try {
			while (rs.next()) {
				System.out.println("rsnext");
				Branch b = new Branch();
				BookCopiesDAO bookCopiesDAO= new BookCopiesDAO();
				b.setBranchId(rs.getInt("branchId"));
				b.setBranchName(rs.getString("branchName"));
				b.setBranchAddress(rs.getString("branchAddress"));
//				b.setCopyList(bookCopiesDAO.readFirstLevel(
//								"select * from tbl_book, tbl_book_copies where tbl_book.bookId IN (select tbl_book_copies.bookId from tbl_book_copies where branchId = ?) and tbl_book.bookId= tbl_book_copies.bookId",
//								new Object[] { b.getBranchId()}));
				branches.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branches;
	}


	public void addBranch(Branch branch) throws SQLException {
		template.update("insert into tbl_library_branch (branchName,branchAddress) values (?,?)", new Object[] { branch.getBranchName(),branch.getBranchAddress() });
	}
	public Branch readBorrowerByID(Integer branchId) throws SQLException {
		List<Branch> branches = template.query("select * from tbl_library_branch where branchId = ?", new Object[] {branchId},this);
		if(branches!=null){
			return branches.get(0);
		}
		return null;
	}
	public void deleteBranch(Integer branchId) throws SQLException {
		System.out.println("delete: "+branchId);
		template.update("delete from tbl_library_branch where branchId = ?", new Object[] {branchId});
	}

}
