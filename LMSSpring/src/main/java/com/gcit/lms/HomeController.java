package com.gcit.lms;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private AdminService adminService;
	@Autowired
	private BorrowerService borrowerService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", "adas" );
		
		return "index";
	}
	@RequestMapping(value = "/selectrole", method = RequestMethod.GET)
	public String selectRole(Model model) throws SQLException {
		return "selectrole";
	}
	@RequestMapping(value = "/selectbranch", method = RequestMethod.GET)
	public String viewBranches(Model model) throws SQLException {
		logger.info("hitting the selectrole");
		return "selectbranch";
	}
	@RequestMapping
	(value = "/realviewbranch", method = RequestMethod.GET)
	public String realviewBranch(Model model) throws SQLException{
		return "realviewbranch";
	}
	@RequestMapping(value = "/addauthor", method = RequestMethod.GET)
	public String addAuthors(Model model) throws SQLException {
		logger.info("hitting the viewAuthors");
		return "addauthor";
	}
	@RequestMapping(value = "/editborrower", method = RequestMethod.GET)
	public String editborrower(Model model) throws SQLException {
		logger.info("editborrower");
		return "editborrower";
	}
	@RequestMapping(value = "/addpublisher", method = RequestMethod.GET)
	public String addPublisher(Model model) throws SQLException {
		logger.info("addpublisher");
		//model.addAttribute("action", "add");
		return "addpublisher";
	}
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
	public String addPublishers(@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhone") String publisherPhone,Model model) throws SQLException {
		logger.info("addPublisher");
		Publisher publisher = new Publisher();
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherPhone(publisherPhone);
		adminService.addPublisher(publisher);
		model.addAttribute("action", "add");
		return "viewpublishers";
	}
	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String addBooks(Model model) throws SQLException {
		logger.info("addbook");
		return "addbook";
	}	
	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST)
	public String addBorrower(@RequestParam("borrowerAddress") String borrowerAddress,
			@RequestParam("borrowerName") String borrowerName,
			@RequestParam("borrowerPhone") String borrowerPhone,Model model) throws SQLException {
		logger.info("addborrower");
		Borrower borrower = new Borrower();
		borrower.setAddress(borrowerAddress);
		borrower.setName(borrowerName);
		borrower.setPhone(borrowerPhone);
		adminService.addBorrower(borrower);
		model.addAttribute("action", "add");
		return "viewborrowers";
	}
	@RequestMapping(value = "/addborrower", method = RequestMethod.GET)
	public String addBorrowers(Model model) throws SQLException {
		logger.info("addborrower");
		return "addborrower";
	}
	@RequestMapping(value = "/editauthor", method = RequestMethod.GET)
	public String editAuthors(Model model) throws SQLException {
		logger.info("editauthor");
		return "editauthor";
	}
	@RequestMapping(value = "/editpublisher", method = RequestMethod.GET)
	public String editPublishers(Model model) throws SQLException {
		logger.info("editauthor");
		return "editpublisher";
	}
	@RequestMapping(value = "/editBorrower", method = RequestMethod.POST)
	public String editAuthor(@RequestParam("borrowerName") String borrowerName,
			@RequestParam("borrowerId") Integer borrowerID, 
			@RequestParam("borrowerPhone") String phone,
			@RequestParam("borrowerAddress") String borrowerAddress,
			Model model) throws SQLException {
		Borrower borrower = new Borrower();
		borrower.setCardNo(borrowerID);
		borrower.setPhone(phone);
		borrower.setAddress(borrowerAddress);
		borrower.setName(borrowerName);
		//System.out.println("editborrower");
		try {
			adminService.editBorrower(borrower);
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		model.addAttribute("action", "edit");
		return "viewborrowers";
		
	}	
	@RequestMapping(value = "/editAuthor", method = RequestMethod.POST)
	public String editAuthor(@RequestParam("authorName") String authorName,
			@RequestParam("authorId") Integer authorId, 
			@RequestParam(value = "bookId", required=false) String [] bookId,
			Model model) throws SQLException {
		logger.info("editauthor");
		Author author = new Author();
		author.setAuthorID(authorId);
		author.setAuthorName(authorName);
		List<Book> books = new ArrayList<Book>();
		//System.out.println(bookId);
		if(bookId!=null){
			for (int i=0;i<bookId.length;i++){
				Integer bookIda = Integer.parseInt(bookId[i].split(" ")[0]);
				String title = "";
				for(int j = 1;j<bookId[i].split(" ").length;j++){
					title+= bookId[i].split(" ")[j];
				}
				Book booklist = new Book();
				booklist.setBookId(bookIda);
				booklist.setTitle(title);
				books.add(booklist);
			}
		}
		author.setBooks(books);
		adminService.editAuthor(author);
		model.addAttribute("action", "edit");
		return "viewauthors";
	}
	@RequestMapping(value = "/editBook", method = RequestMethod.POST)
	public String editBook(@RequestParam("title") String bookName,
			@RequestParam("bookId") Integer bookId, 
			@RequestParam(value = "authorId", required=false) String [] authorId,
			@RequestParam(value = "genreId", required=false) String [] genreId,
			@RequestParam(value = "publisherId", required=false) String publisherId,
			Model model) throws SQLException {
		logger.info("editauthor");
		Book book = new Book();
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();
		if(authorId!=null){
			for (int i=0;i<authorId.length;i++){

				Integer authorIds = Integer.parseInt(authorId[i].split(" ")[0]);
				String authorName1 = "";
				for(int j = 1;j<authorId[i].split(" ").length;j++){
					authorName1+=authorId[i].split(" ")[j];
				}
				System.out.println("id:"+authorId);
				System.out.println("name:"+authorId);
				Author authornew = new Author();
				authornew.setAuthorID(authorIds);
				authornew.setAuthorName(authorName1);
				authors.add(authornew);
			}
		}
		if(genreId!=null){
			for (int i=0;i<genreId.length;i++){

				Integer genreIds = Integer.parseInt(genreId[i].split(" ")[0]);
				String genreName = "";
				for(int j = 1;j<genreId[i].split(" ").length;j++){
					genreName+= genreId[i].split(" ")[j];
				}
				System.out.println("id:"+genreId);
				System.out.println("name:"+genreId);
				Genre genre = new Genre();
				genre.setGenreId(genreIds);
				genre.setGenreName(genreName);
				genres.add(genre);
			}
		}
		book.setBookId(bookId);
		book.setAuthors(authors);
		book.setGenres(genres);
		book.setTitle(bookName);
		System.out.println(bookName);
		book.setPid(Character.getNumericValue(publisherId.charAt(0)));
		try {
			adminService.editBook(book);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("action", "edit");
		return "viewbooks";
	}
	@RequestMapping(value = "/viewauthors", method = RequestMethod.GET)
	public String viewAuthors(Model model) throws SQLException {
		logger.info("viewauthors");
		model.addAttribute("action", "view");
		return "viewauthors";
	}
	@RequestMapping(value = "/viewpublishers", method = RequestMethod.GET)
	public String viewPublisher(Model model) throws SQLException {
		logger.info("viewpublishers");
		return "viewpublishers";
	}
	@RequestMapping(value = "/viewbooks", method = RequestMethod.GET)
	public String viewBooks(Model model) throws SQLException{
		return "viewbooks";
	}
	@RequestMapping(value = "/borrowborrower", method = RequestMethod.GET)
	public String borrowborrower(Model model) throws SQLException{
		return "borrowborrower";
	}
	@RequestMapping(value = "/viewreserve", method = RequestMethod.GET)
	public String viewreserve(Model model) throws SQLException{
		return "viewreserve";
	}
	@RequestMapping(value = "/reserveborrower", method = RequestMethod.GET)
	public String reserveborrower(Model model) throws SQLException{
		return "reserveborrower";
	}
	@RequestMapping(value = "/borrowermain", method = RequestMethod.GET)
	public String borrowermain(Model model) throws SQLException{
		return "borrowermain";
	}
	@RequestMapping(value = "/viewbranches", method = RequestMethod.GET)
	public String viewbranches(Model model) throws SQLException{
		return "viewbranches";
	}
	@RequestMapping(value = "/addbranch", method = RequestMethod.GET)
	public String addbranch(Model model) throws SQLException{
		return "addbranch";
	}	
	@RequestMapping(value = "/editbookcopy", method = RequestMethod.GET)
	public String editbookcopy(Model model) throws SQLException{
		return "editbookcopy";
	}
	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
	public String borrower(Model model) throws SQLException{
		return "borrower";
	}
	@RequestMapping(value = "/viewborrowers", method = RequestMethod.GET)
	public String viewBorrowers(Model model) throws SQLException{
		return "viewborrowers";
	}
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
	public String addAuthor(@RequestParam("authorName") String authorName,@RequestParam(value = "bookId", required=false) String [] bookId,Model model) throws SQLException {
		logger.info("addAuthor");
		Author author = new Author();
		author.setAuthorName(authorName);
		List<Book> books = new ArrayList<Book>();
		//System.out.println(bookId);
		System.out.println("success: "+authorName);
		if(bookId!=null){
			for (int i=0;i<bookId.length;i++){
				Integer bookIda = Integer.parseInt(bookId[i].split(" ")[0]);
				String title = "";
				for(int j = 1;j<bookId[i].split(" ").length;j++){
					title+= bookId[i].split(" ")[j];
				}
				Book booklist = new Book();
				booklist.setBookId(bookIda);
				booklist.setTitle(title);
				books.add(booklist);
			}
		}
		author.setBooks(books);
		adminService.addAuthor(author);
		model.addAttribute("action", "add");
		return "viewauthors";
	}
	@RequestMapping(value = "/addBranch", method = RequestMethod.POST)
	public String addAuthor(@RequestParam("branchName") String branchName,
			@RequestParam(value = "branchAddress", required=false) String branchAddress,
			Model model) throws SQLException {
		logger.info("addAuthor");
		Branch branch = new Branch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		adminService.addBranch(branch);
		model.addAttribute("action", "add");
		return "viewbranches";
	}
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addBook(@RequestParam("booktitle") String bookName,@RequestParam(value = "authorId", required=false) String [] authorId,
			@RequestParam(value = "genreId", required=false) String [] genreId,
			@RequestParam(value = "publisherId", required=false) String publisherId,
			@RequestParam(value = "action", required=false) String action	,Model model) throws SQLException {
		logger.info("addbook");
		Book book = new Book();
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();
		if(authorId!=null){
			for (int i=0;i<authorId.length;i++){
				Integer authorId1 = Integer.parseInt(authorId[i].split(" ")[0]);
				String authorName1 = "";
				for(int j = 1;j<authorId[i].split(" ").length;j++){
					authorName1+= authorId[i].split(" ")[j];
				}
				Author authornew = new Author();
				authornew.setAuthorID(authorId1);
				authornew.setAuthorName(authorName1);
				authors.add(authornew);
			}
		}
		if(genreId!=null){
			for (int i=0;i<genreId.length;i++){

				Integer genreId1 = Integer.parseInt(genreId[i].split(" ")[0]);
				String genreName = "";
				for(int j = 1;j<genreId[i].split(" ").length;j++){
					genreName+= genreId[i].split(" ")[j];
				}
				Genre genre = new Genre();
				genre.setGenreId(genreId1);
				genre.setGenreName(genreName);
				genres.add(genre);
			}
		}
		book.setAuthors(authors);
		book.setGenres(genres);
		book.setTitle(bookName);
		String[] parts = publisherId.split(" ");
		String pid = parts[0]; 
		book.setPid(Integer.parseInt(pid));
		try {
			adminService.addBook(book);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("action", "add" );
		return "viewbooks";
	}
	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.GET)
	public String deleteAuthor(@RequestParam("authorId") Integer authorId, Model model) throws SQLException {
		logger.info("deleteauthor");
		adminService.deleteAuthor(authorId);
		model.addAttribute("action", "delete");
		return "viewauthors";
	}
	@RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
	public String deleteBook(@RequestParam("bookId") Integer bookId,
			@RequestParam(value = "action", required=false) String action	,Model model) throws SQLException {
		logger.info("deleteBook");
		adminService.deleteBook(bookId);
		model.addAttribute("action", "delete" );
		return "viewbooks";
	}
	@RequestMapping(value = "/deleteBranch", method = RequestMethod.GET)
	public String deleteBranch(@RequestParam("branchId") Integer branchId
			,Model model) throws SQLException {
		logger.info("deleteBranch");
		adminService.deleteBranch(branchId);
		model.addAttribute("action", "delete" );
		return "viewbranches";
	}
	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.GET)
	public String deleteBorrower(@RequestParam("cardNo") Integer cardNo,
			Model model) throws SQLException {
		logger.info("deleteBook");
		adminService.deleteBorrower(cardNo);
		model.addAttribute("action", "delete" );
		return "viewbooks";
	}
	@RequestMapping(value = "/deletePublisher", method = RequestMethod.GET)
	public String deletePublisher(@RequestParam("publisherId") Integer publisherId,
			Model model) throws SQLException {
		logger.info("deletePublisher");
		adminService.deletePublisher(publisherId);
		model.addAttribute("action", "delete" );
		return "viewpublishers";
	}
	@RequestMapping(value = "/librarianmain", method = RequestMethod.GET)
	public String librarianmain(Model model){
		model.addAttribute("action", "view");
		return "librarianmain";
	}
	@RequestMapping(value = "/editbranch", method = RequestMethod.GET)
	public String editbranch(Model model){
		return "editbranch";
	}
	@RequestMapping(value = "/addcopies", method = RequestMethod.GET)
	public String addcopies(Model model){
		return "addcopies";
	}
	@RequestMapping(value = "/addcopiesmain", method = RequestMethod.GET)
	public String addcopiesmain(Model model){
		return "addcopiesmain";
	}
	@RequestMapping(value = "/checkoutbook", method = RequestMethod.GET)
	public String checkoutbook(Model model){
		return "checkoutbook";
	}
	@RequestMapping(value = "/checkhistory", method = RequestMethod.GET)
	public String checkhistory(Model model){
		return "checkhistory";
	}
	@RequestMapping(value = "/returnLoans", method = RequestMethod.GET)
	public String returnLoans(
			@RequestParam ("cardNo") Integer cardNo,
			@RequestParam ("bookId") Integer bookID,
			@RequestParam ("branchId") Integer branchId,
			Model model) throws SQLException{
		borrowerService.returnBook(cardNo,bookID, branchId);
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("action", 0);
		return "borrowermain";
	}
	@RequestMapping(value = "/returnbook", method = RequestMethod.GET)
	public String returnbook(Model model){
		return "returnbook";
	}
	@RequestMapping(value = "/varifyCard", method = RequestMethod.POST)
	public String varifyCard (
			@RequestParam (value= "cardNo", required=false) Integer cardNo,
			Model model){
		System.out.println("varifyCard");
		System.out.println(cardNo);
		{
			try {
				if(borrowerService.varifyCard(cardNo)){
					System.out.println("varified!!!!!!!!!!");
					return "borrowermain";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("cardNo", cardNo);
		return "borrower";
	}
	@RequestMapping(value = "/varifyCardBorrower", method = RequestMethod.GET)
	public String varifyCardBorrower (
			@RequestParam ("bookId") Integer bookId,
			@RequestParam ("branchId") Integer branchId,
			@RequestParam (value= "cardNo", required=false) Integer cardNo,
			Model model){
		System.out.println("varifyCard");
		System.out.println(cardNo);
		int k=0;
		{
			try {
				if(borrowerService.varifyCard(cardNo)){
					System.out.println("varified!!!!!!!!!!");
					model.addAttribute("bookId", bookId);
					model.addAttribute("branchId", branchId);
					String s = borrowBook(cardNo,bookId, branchId, model);
					if(s.equals("fail")){
						model.addAttribute("result", "fail");
						return "realviewbranch";
					}
				}
			} catch (SQLException e) {
				System.out.println("EeeeeeeeeEEEEEEborrowBook");
				//e.printStackTrace();
				k=-1;
				System.out.println("kvalue: "+k);
				model.addAttribute("action", 0);
				return "borrowermain";
			}
		}
		
		int i =1;
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("action", i);
		return "borrowermain";
	}	
	@RequestMapping(value = "/varifyCardReserve", method = RequestMethod.GET)
	public String varifyCardReserve (
			@RequestParam  ("bookId") Integer bookId,
			@RequestParam  ("branchId") Integer branchId,
			@RequestParam (value= "cardNo", required=false) Integer cardNo,
			Model model){
		System.out.println("varifyCard");
		System.out.println(cardNo);
		{
			try {
				if(borrowerService.varifyCard(cardNo)){
					System.out.println("varified!!!!!!!!!!");
					reserveBook(cardNo, bookId, branchId, model);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("cardNo", cardNo);
		return "borrowermain";
	}
	@RequestMapping(value = "/reserveBook", method = RequestMethod.GET)
	public String reserveBook (
			@RequestParam ("cardNo") Integer cardNo,
			@RequestParam ("bookId") Integer bookId,
			@RequestParam ("branchId") Integer branchId,
			Model model){
		try {
			System.out.println("variables card book branch:"+cardNo + " " + bookId + " " + branchId);
			int k = borrowerService.reserveBook(cardNo, bookId, branchId);
			if(k==-1){
				System.out.println("FFFFFFFFF");
				//return "fail";
				}
				
			else{
				System.out.println("IIIIIIIIIIIIII");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EeeeeeeeeEEEEEEborrowBook");
		}
		int i =1;
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("action", i);
		return "borrowermain";
	}	
	@RequestMapping(value = "/borrowBook", method = RequestMethod.GET)
	public String borrowBook (
			@RequestParam ("cardNo") Integer cardNo,
			@RequestParam ("bookId") Integer bookId,
			@RequestParam ("branchId") Integer branchId,
			Model model){
		try {
			int k = borrowerService.checkoutBook(cardNo, bookId, branchId);
			if(k==-1){
				System.out.println("FFFFFFFFF");
				return "fail";
				}
				
			else{
				System.out.println("IIIIIIIIIIIIII");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EeeeeeeeeEEEEEEborrowBook");
		}
		int i =1;
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("action", i);
		return "borrowermain";
	}
	@RequestMapping(value = "/editCopies", method = RequestMethod.POST)
	public String editCopies(
			@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("noOfCopies") Integer noOfCopies,
			Model model){
		System.out.println("editCopies");
//		Integer noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
//		bookId = Integer.parseInt(request.getParameter("bookId"));
//		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		BookCopies bookCopies = new BookCopies();
		bookCopies.setBookId(bookId);
		bookCopies.setNoOfCopies(noOfCopies);
		bookCopies.setBranchId(branchId);
		try {
			adminService.updateCopies(bookCopies);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("branchId", branchId);
		model.addAttribute("action","add");
		return "librarianmain";
	}	
	@RequestMapping(value = "/editBranch", method = RequestMethod.POST)
	public String editCopies(
			@RequestParam("branchId") Integer branchId,
			@RequestParam("branchName") String branchName,
			@RequestParam("branchAddress") String branchAddress,
			Model model){
		System.out.println("editBranch");
		Branch branch = new Branch();
		branch.setBranchAddress(branchAddress);
		branch.setBranchName(branchName);
		branch.setBranchId(branchId);
		try {
			adminService.updateBranch(branch);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		model.addAttribute("branchId", branchId);
		model.addAttribute("action", "edit");
		return "librarianmain";
	}	
	@RequestMapping(value = "/overrideduedate", method = RequestMethod.GET)
	public String overrideduedate(
			Model model) throws SQLException {
		logger.info("overrideduedate");
		//adminService.deletePublisher(publisherId);
		//model.addAttribute("action", "delete" );
		return "overrideduedate";
	}
	@RequestMapping(value = "/overridedue", method = RequestMethod.GET)
	public String overridedue(
			Model model) throws SQLException {
		logger.info("overridedue");
		//adminService.deletePublisher(publisherId);
		//model.addAttribute("action", "delete" );
		return "overridedue";
	}
	@RequestMapping(value = "/overrideDue", method = RequestMethod.POST)
	public String overrideDue(
			@RequestParam("datepicker") String date,
			@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("cardNo") Integer cardNo,
			Model model) throws SQLException {
		logger.info("overridedue");
		//adminService.deletePublisher(publisherId);
		System.out.println("overrideDue");
		String month = date.substring(0, 2);
		String perdate = date.substring(3, 5);
		String year = date.substring(6, 10);
		String duedate = year+"-"+month+"-"+perdate+" 00:00:00";
		System.out.println(duedate);					
		try {
			borrowerService.updateDueDate(duedate, cardNo, branchId, bookId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//forwardPage = "overrideduedate.jsp";
		model.addAttribute("action", "override" );
		return "overrideduedate";
	}
}
