<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
	String publisherName = request.getParameter("publisherName");
	String publisherAddress = request.getParameter("publisherAddress");
	String publisherPhone = request.getParameter("publisherPhone");
	System.out.println("cardno: "+publisherId);
%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Publisher Details to Edit</h3>
	<form action="editPublisher" method="post">
		Enter Publisher Name: <br />
			<input type="text" name="publisherName"
			value="<%=publisherName%>"><br/> 
			Enter Publisher Address: <br />
			<input type="text" name="publisherAddress"
			value="<%=publisherAddress%>"><br/> 	
			Enter Publisher Phone: <br />
			<input type="text" name="publisherPhone"
			value="<%=publisherPhone%>"><br/> 			
			<input type="hidden" name="publisherId" value="<%=publisherId%>">
		<button type="submit">Edit Publisher!</button>
	</form>
</div>