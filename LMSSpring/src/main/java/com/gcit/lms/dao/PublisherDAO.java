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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{

	public void addPublisher(Publisher publisher) throws SQLException {
		template.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone() });
	}
	
	public Integer addPublisherWithID(Publisher publisher) throws SQLException {
		//return saveWithID("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone() });
		final String publisherName = publisher.getPublisherName();
		final String publisherAddress = publisher.getPublisherAddress();
		final String publisherPhone = publisher.getPublisherPhone();
		final String INSERT_SQL = "insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "publisherName","publisherAddress","publisherPhone" });
				ps.setString(1, publisherName);
				ps.setString(2, publisherAddress);
				ps.setString(3, publisherPhone);
				return ps;
			}
		}, keyHolder);
		Integer publisherId = keyHolder.getKey().intValue();
		return publisherId;
	}
	/*
	public void updateBookPublisher(Publisher publisher) throws SQLException {
		for(Book b: publisher.getBook()){
			save("insert into tbl_book_publishers (bookId, publisherId) values (?, ?)", new Object[] { publisher.getPublisherId(), b.getBookId() });
		}
	}
	*/
	public void updatePublisher(Publisher publisher) throws SQLException {
		template.update("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws SQLException {
		template.update("delete from tbl_publisher where publisherId = ?", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers(Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		return template.query("select * from tbl_publisher", this);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			while (rs.next()) {
				Publisher a = new Publisher();
				a.setPublisherId(rs.getInt("publisherId"));
				a.setPublisherName(rs.getString("publisherName"));
				a.setPublisherAddress(rs.getString("publisherAddress"));
				a.setPublisherPhone(rs.getString("publisherPhone"));
				/*
				a.setBooks(bdao.readFirstLevel(
						"select * from tbl_publisher where pId = ?",
						new Object[] { a.getPublisherId()}));
						*/
				publishers.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publishers;
	}

//	@Override
//	public List<Publisher> extractDataFirstLevel(ResultSet rs) {
//		List<Publisher> publishers = new ArrayList<Publisher>();
//		try {
//			while (rs.next()) {
//				Publisher a = new Publisher();
//				a.setPublisherId(rs.getInt("publisherId"));
//				a.setPublisherName(rs.getString("publisherName"));
//				publishers.add(a);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return publishers;
//	}

	public void deletePublisher(Integer publisherID) throws SQLException {
		template.update("delete from tbl_publisher where publisherId = ?", new Object[] {publisherID});
	}
}
