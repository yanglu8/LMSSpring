<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="branch.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	AdminService service = new AdminService();
	Integer count;
	//String search = request.getParameter("searchBranches");
	Branch b = new Branch();
	List<BookCopies> bCopies = service.viewBranchById(branchId);
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
	
<div class="jumbotron container" name="headerdiv">
	<h1><a href="http://localhost:8080/LMSWeb/" >Library</a>
		
		<a href=<%="librarianmain.jsp?branchId="+Integer.parseInt(request.getParameter("branchId")) %> class="btn btn-info pull-right" role="button">I am a Librarian</a>
	</h1>

	<p>The quieter you become, the more you're able to hear.</p>

</div>
<table id="myTable" class="table table-striped">
	<thead>
		<tr>
			<th>BookId</th>
			<th>Title</th>
			<th>No of Copies</th>
			<th>Borrow</th>
		</tr>
	</thead>
	<tbody>
				<% for (BookCopies bc : bCopies) {
					//Author author = new Author();
					
				%>
				<tr>
					<td><%=bc.getBookId()%></td>
					<td><%=bc.getBookName()%></td>
					<td><%=bc.getNoOfCopies()%></td>
					<td></td>
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