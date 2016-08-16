<%@ page import="com.gcit.lms.entity.Book"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer bookID = Integer.parseInt(request.getParameter("bookId"));
	Book book = null;
	AdminService service = new AdminService();
	book = service.viewBookByID(bookID);
%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Book Details to Edit</h3>
	<form action="editBook" method="post">
		Enter Book Name: 
		<br />
		<input type="text" name="title"
			value="<%=book.getTitle()%>"><br /> <input
			type="hidden" name="bookId" value="<%=book.getBookId()%>">
		<button type="submit">Edit Book!</button>
	</form>
</div>




