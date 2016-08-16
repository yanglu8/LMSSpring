<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Integer count;
	Object action = null;
	if(request.getAttribute("action")!=null){
		action = request.getAttribute("action");
	}

	List<Author> authors = new ArrayList<Author>();
	if(request.getAttribute("authors")!=null){
		authors = (List<Author>)request.getAttribute("authors");
	}else{
		authors = service.viewAuthors(1);	
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
			<th>AuthorId</th>
			<th>Title</th>
			<th>Books</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>

				<%
					for (Author a : authors) {
						String title= "";
						//String genreNames = "";
						if(a.getBooks()!=null){
							for (Book book :a.getBooks()){
								title += book.getTitle()+", ";
							}
					}
				%>
				<tr>
					<td><%=a.getAuthorID()%></td>
					<td><%=a.getAuthorName()%></td>
					<td><%=title%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" 
							onclick="javascript:location.href='editauthor?authorId=<%=a.getAuthorID()%>'">Edit</button></td>
					<td><button name="Delete" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorID()%>'">Delete</button></td>
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