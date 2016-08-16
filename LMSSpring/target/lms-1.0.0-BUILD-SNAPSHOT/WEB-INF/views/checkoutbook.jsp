<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%@include file ="./css/table.css" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.BorrowerService"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%
	BorrowerService service = new BorrowerService();
	Integer cardNo =Integer.parseInt(request.getParameter("cardNo"));
	//String search = request.getParameter("searchBooks");
	List<BookCopies> bookCopies = new ArrayList<BookCopies>();
	bookCopies = service.viewBooks(cardNo);
	if (bookCopies== null ){
		System.out.println("error!!");	
	}
	//System.out.println(bookCopies.get(0).getBookName());	
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
<div class="centersized">
<table id="myTable" class="table table-striped">
	<thead>
		<tr>
			<th>BookId</th>
			<th>Title</th>
			<th>BranchName</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
		<%
		if(bookCopies.size()!=0){
					for (BookCopies b : bookCopies) {
				%>
		<tr>
			<td><%=b.getBookId()%></td>
			<td><%=b.getBookName()%></td>
			<td><%=b.getBranchId()%></td>
			<td><button name="Edit" class="btn btn-sm btn-success"
					onclick="javascript:location.href='borrowBook?bookId=<%=b.getBookId()%>&cardNo=<%= cardNo %>&branchId=<%=b.getBranchId()%>'">Borrow</button></td>
		</tr>
		<%
					}}
				%>
	</tbody>
</table>
</div>
<script>
$(document).ready(function(){
    $('#myTable').dataTable();
});
</script>
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>