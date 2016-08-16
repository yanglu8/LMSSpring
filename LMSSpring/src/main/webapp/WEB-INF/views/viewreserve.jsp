<%@page import="com.gcit.lms.entity.BookReserve"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	BorrowerService service = (BorrowerService) ac.getBean("borrowerService");
	Integer cardNo =Integer.parseInt(request.getParameter("cardNo"));
	List<BookReserve> books = new ArrayList<BookReserve>();
	books = service.viewBookReserve(cardNo);
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
			<th>Reserve Date</th>
			<th>Available</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (BookReserve b : books) {
				%>
				<tr>
					<td><%=b.getBookId()%></td>
					<td><%=b.getTitle()%></td>
					<td><%=b.getBranchName()%></td>
					<td><%=b.getDateReserve()%></td>
					<%
					if (b.getAvailable()==0){
						%>
							<td><button name="Edit" class="btn btn-sm btn-danger"  disabled
							onclick="javascript:location.href='returnLoans?bookId=<%=b.getBookId()%>&branchId=<%= b.getBranchId() %>&cardNo=<%= cardNo %>'">Not Available For Now!</button></td>
						
						<%
					}else{
						%>
						<td><button name="Edit" class="btn btn-sm btn-success"
						onclick="javascript:location.href='returnLoans?bookId=<%=b.getBookId()%>&branchId=<%= b.getBranchId() %>&cardNo=<%= cardNo %>'">Borrow!</button></td>
					
					<%
					}
					%>
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