<%@ page import="com.gcit.lms.entity.Borrower"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer BorrowerID = Integer.parseInt(request.getParameter("borrowerId"));
	Borrower Borrower = null;
	AdminService service = new AdminService();
	Borrower = service.viewBorrowerByID(BorrowerID);
%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Borrower Details to Edit</h3>
	<form action="deleteBorrower" method="post">
		Enter Borrower Name: <input type="text" name="borrowerName"
			value="<%=Borrower.getName()%>"><br /> <input
			type="hidden" name="borrowerId" value="<%=Borrower.getCardNo()%>">
		<button type="submit">Delete Borrower!</button>
	</form>
</div>