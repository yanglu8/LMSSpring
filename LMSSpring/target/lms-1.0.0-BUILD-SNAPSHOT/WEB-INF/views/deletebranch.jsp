<%@ page import="com.gcit.lms.entity.Branch"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer branchID = Integer.parseInt(request.getParameter("branchId"));
	Branch branch = null;
	AdminService service = new AdminService();
	branch = service.viewBranchByID(branchID);
%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Branch Details to Edit</h3>
	<form action="deleteBranch" method="post">
		Enter Branch Name: <input type="text" name="branchName"
			value="<%=branch.getBranchName()%>"><br /> <input
			type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
		<button type="submit">Delete Branch!</button>
	</form>
</div>