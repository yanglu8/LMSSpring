<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	BookCopies bookCopies = new BookCopies();
	bookCopies.setBookId(bookId);
	bookCopies.setBranchId(branchId);
%>
<div>
	<h2>Add Books</h2>
	<h3>Enter the Number of Copies</h3>
	<form action="editCopies" method="post">
			Enter Copies Number: <br />
			<input type="text" name="noOfCopies" ><br/> 			
			<input type="hidden" name="bookId" value="<%=bookCopies.getBookId()%>">
			<input type="hidden" name="branchId" value="<%=bookCopies.getBranchId()%>">
		<button type="submit">Add Copies!</button>
	</form>
</div>