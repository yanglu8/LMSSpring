<%@include file="librarian.html"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	AdminService service = new AdminService();
	Integer count;
	//String search = request.getParameter("searchAuthors");
	String branchId = request.getParameter("branchId");
%>
<div>
	<div align="center">
	<h2>
		Choose your action.
	</h2>


	

  <a href="editbranch.jsp?branchId=<%=branchId%>"><button type="button" class="btn btn-primary btn-lg">Edit Branch</button></a>
<br><br><br>
  <a href="addcopies.jsp?branchId=<%=branchId%>"> <button type="button" class="btn btn-primary btn-lg">Add Copies to Branch</button>
    </div>