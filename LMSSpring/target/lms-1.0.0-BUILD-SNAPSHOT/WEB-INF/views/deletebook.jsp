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
	<form action="deleteBook" method="post">
		Enter Book Name: <input type="text" name="bookName"
			value="<%=book.getTitle()%>"><br /> <input
			type="hidden" name="bookId" value="<%=book.getTitle()%>">
		<button type="submit">Delete Book!</button>
	</form>
</div>