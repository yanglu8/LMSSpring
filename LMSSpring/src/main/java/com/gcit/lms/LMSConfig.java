package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BookReserveDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;;
@Configuration
public class LMSConfig {
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/library";
	private static String user = "root";
	private static String pass = "";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		return ds;
	}
	
	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	@Bean
	public BranchDAO brdao(){
		return new BranchDAO();
	}
	@Bean
	public BookCopiesDAO bcdao(){
		return new BookCopiesDAO();
	}
	@Bean
	public BorrowerDAO bodao(){
		return new BorrowerDAO();
	}
	@Bean
	public BookLoansDAO bldao(){
		return new BookLoansDAO();
	}
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	@Bean
	public PublisherDAO pdao(){
		return new PublisherDAO();
	}
	@Bean
	public BookReserveDAO bkrdao(){
		return new BookReserveDAO();
	}
	@Bean(name = "adminService")
	public AdminService AdminService() {
		return new AdminService();
	}
	@Bean(name = "borrowerService")
	public BorrowerService BorrowerService() {
		return new BorrowerService();
	}
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource());
		return tx;
	}
	
}
