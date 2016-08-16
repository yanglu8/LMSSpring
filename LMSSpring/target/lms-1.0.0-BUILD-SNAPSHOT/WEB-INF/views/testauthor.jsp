<%@include file="include.html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Book"%>
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
<%
	AdminService service = new AdminService();
	Integer count;
	//String search = request.getParameter("searchBooks");
	List<Book> books = new ArrayList<Book>();
	if(request.getAttribute("books")!=null){
		books = (List<Book>)request.getAttribute("books");
	}else{
		books = service.viewBooks(1);	
	}
	//search = request.getParameter("searchBooks");
%>
	<thead>
		<tr>
			<th>BookId</th>
			<th>Title</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (Book b : books) {
				%>
				<tr>
					<td><%=b.getBookId()%></td>
					<td><%=b.getTitle()%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" data-toggle="modal" data-target="#myModal"
							href='editbook.jsp?bookId=<%=b.getBookId()%>'>Edit</button></td>
					<td><button name="Delete" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>'">Delete</button></td>
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