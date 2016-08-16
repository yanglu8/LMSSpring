<%@include file="admin.html" %>
<%@ page import="com.gcit.lms.entity.Borrower"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Integer cardNo = Integer.parseInt(request.getParameter("borrowerId"));
	System.out.println("cardno: "+cardNo);
	Borrower borrower = null;
	
	borrower = service.viewBorrowerByID(cardNo);
	if(borrower == null){
		System.out.println("errrrrrr");
	}
%>
<div align="center">
	<h2>Hello Admin!</h2>
	<h3>Enter Borrower Details to Edit</h3>
	<form action="editBorrower" method="post">
		Enter Borrower Name: <br />
			<input type="text" name="borrowerName"
			value="<%=borrower.getName()%>"><br/> 
			Enter Borrower Address: <br />
			<input type="text" name="borrowerAddress"
			value="<%=borrower.getAddress()%>"><br/> 
			Enter Borrower Phone: <br />
			<input type="text" name="borrowerPhone"
			value="<%=borrower.getPhone()%>"><br/> 			
			<input type="hidden" name="borrowerId" value="<%=borrower.getCardNo()%>">
		<button type="submit">Edit Borrower!</button>
	</form>
</div>