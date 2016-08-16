<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%
	
	AdminService service = new AdminService();
	Integer count;
	String search = request.getParameter("searchAuthors");
	System.out.println(search+"ccc");
	if (search==null||search.equals("null")){
		System.out.println(search+"aaa");
		count = service.getAuthorCount();
	}
	else {
		count = service.getSearchCount(search);
		System.out.println(count+"bbb");
	}
	int pageNos = 0;
	
	Integer pageSize = Integer.valueOf(10);
	if(count%pageSize > 0){
		pageNos = count/10 + 1;
	}else{
		pageNos = count/10;
	}
	pageNos = 5;
	List<Author> authors = new ArrayList<Author>();
	if(request.getAttribute("authors")!=null){
		authors = (List<Author>)request.getAttribute("authors");
	}else{
		authors = service.viewAuthors(1);	
	}
	search = request.getParameter("searchAuthors");
%>
<h2>Hello Admin!</h2>
<h3>Below are a list of Authors in LMS.</h3>
<div class="input-group">
	<form action="viewAuthors">
		<span class="input-group-addon" id="basic-addon1">Go!</span> <input
			type="text" class="form-control" placeholder="Search Author"
			aria-describedby="basic-addon1" name ="searchAuthors">
	</form>
</div>
<nav aria-label="Page navigation">
  <ul class="pagination">
  	<%for(int i=1; i<=pageNos;i++){ %>
    <li><a href="pageAuthors?searchAuthors=<%=search%>&pageNo=<%=i%>"><%=i%></a></li>
    <%} %>
  </ul>
</nav>
<div class="row">
	<div class="col-md-6">
		<table class="table">
			<thead>
				<tr>
					<th>Author ID</th>
					<th>Author Name</th>
					<th>Edit Author</th>
					<th>Delete Author</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Author a : authors) {
				%>
				<tr>
					<td><%=a.getAuthorID()%></td>
					<td><%=a.getAuthorName()%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" data-toggle="modal" data-target="#myModal"
							href='editauthor.jsp?authorId=<%=a.getAuthorID()%>'>Edit</button></td>
					<td><button name="Delete" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorID()%>'">Delete</button></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</div>

<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">

    </div>
  </div>
</div>