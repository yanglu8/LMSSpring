<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer bookID = Integer.parseInt(request.getParameter("bookId"));
	Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	BorrowerService service = new BorrowerService();
	service.checkoutBook(cardNo,bookID, branchId);
%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Book Details to Edit</h3>
	<form action="checkoutBook" method="post">
	</form>
</div>