<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.BookCopies"%>
<%
	BorrowerService service = new BorrowerService();
	Integer cardNo =Integer.parseInt(request.getParameter("cardNo"));
	List<BookLoans> books = new ArrayList<BookLoans>();
	books = service.viewBookLoans(cardNo);
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
<table id="myTable" class="table table-striped">
	<thead>
		<tr>
			<th>BookId</th>
			<th>Title</th>
			<th>Branch</th>
			<th>Due Date</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (BookLoans b : books) {
				%>
				<tr>
					<td><%=b.getBookId()%></td>
					<td><%=b.getTitle()%></td>
					<td><%=b.getBranchName()%></td>
					<td><%=b.getDueDate()%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" 
							onclick="javascript:location.href='returnLoans?bookId=<%=b.getBookId()%>&branchId=<%= b.getBranchId() %>&cardNo=<%= cardNo %>'">Return</button></td>
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