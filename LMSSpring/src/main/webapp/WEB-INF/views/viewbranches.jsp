<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Integer count;
	//String search = request.getParameter("searchBranches");
	List<Branch> branches = new ArrayList<Branch>();
	branches = service.viewBranches(1);
	Object action = null;
	if(request.getAttribute("action")!=null){
		action = request.getAttribute("action");
	}
%>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
</style>
<script type="text/javascript"
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<%
	if(action!=null){
	%>
	
			<%if (action.equals("add")){
			%>
		<div class="alert alert-success">
	    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    <strong>Success! </strong>   You just add a book!!
		</div>
			<%
		} %>
			<%if (action.equals("delete")){
			%>
	  <div class="alert alert-danger">
	    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    <strong>Success!</strong> You Just Delete a Book!
	  </div>
			<%
		} %>
				<%if (action.equals("edit")){
			%>
	  <div class="alert alert-warning">
	    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    <strong>Success!</strong> You Just Edit a Book!
	  </div>
			<%
		} %>
		
	<%
	}
	%>
	
<table id="myTable" class="table table-striped">
	<thead>
		<tr>
			<th>BranchId</th>
			<th>BranchName</th>
			<th>Book List</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (Branch b : branches) {
						String title= "";
						if(b.getCopyList()!=null){
							for (BookCopies book :b.getCopyList()){
								title += book.getBookName()+", ";
							}
					}
				%>
				<tr>
					<td><%=b.getBranchId()%></td>
					<td><%=b.getBranchName()%></td>
					<td><%=title%></td>
					<td><button name="Delete" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deleteBranch?branchId=<%=b.getBranchId()%>'">Delete</button></td>
				</tr>
				<%
					}
				%>
	</tbody>
</table>
<script>
$(document).ready(function(){
    $('#myTable').dataTable();
});
</script>
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">

    </div>
  </div>
</div>