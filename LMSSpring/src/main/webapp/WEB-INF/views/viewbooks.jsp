<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.entity.Genre"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Object action = null;
	if(request.getAttribute("action")!=null){
		action = request.getAttribute("action");
	}
	List<Book> books = new ArrayList<Book>();
	if(request.getAttribute("books")!=null){
		books = (List<Book>)request.getAttribute("books");
	}else{
		books = service.viewBooks(1);	
	}
	//System.out.println(books.get(1).getBookId()+" "+books.get(1).getTitle());
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
			<th>BookId</th>
			<th>Title</th>
			<th>Authors</th>
			<th>Genres</th>
			<th>Publisher</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (Book b : books) {
						//Author author = new Author();
						String authorNames= "";
						String genreNames = "";
						if(b.getAuthors()!=null){
							for (Author author :b.getAuthors()){
								authorNames += author.getAuthorName()+", ";
							}
						}
						if(b.getGenres()!=null){
							for (Genre genre :b.getGenres()){
								genreNames += genre.getGenreName()+", ";
							}
						}
				%>
				<tr>
					<td><%=b.getBookId()%></td>
					<td><%=b.getTitle()%></td>
					<td><%=authorNames%></td>
					<td><%=genreNames%></td>
					<td><%=b.getPid()%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" 
							onclick="javascript:location.href='editbookcopy?bookId=<%=b.getBookId()%>&title=<%=b.getTitle()%>'">Edit</button></td>
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
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">

    </div>
  </div>
</div>