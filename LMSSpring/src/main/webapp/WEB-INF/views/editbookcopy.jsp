<%@page import="com.gcit.lms.entity.Genre"%>
<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@ page import="com.gcit.lms.entity.Genre"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	//Integer count = service.getAuthorCount();
	//int pageNos = 0;
	//Integer pageSize = Integer.valueOf(10);
	List<Author> authors = new ArrayList<Author>();
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = service.viewAuthors(1);
	}
	List<Branch> branches = new ArrayList<Branch>();
	if(request.getAttribute("branches")!=null){
		branches = (List<Branch>)request.getAttribute("branches");
	}else{
		branches = service.viewBranches(1);	
	}
	List<Publisher> publishers = new ArrayList<Publisher>();
	if(request.getAttribute("pulishers")!=null){
		publishers = (List<Publisher>)request.getAttribute("pulishers");
	}else{
		publishers = service.viewPublishers(1);	
	}
	List<Genre> genres = new ArrayList<Genre>();
	if(request.getAttribute("genres")!=null){
		genres = (List<Genre>)request.getAttribute("genres");
	}else{
		genres = service.viewGenres(1);	
	}
	Integer bookID = Integer.parseInt(request.getParameter("bookId"));
	Book book = null;
	book = service.viewBookByID(bookID);
%>
<div align="center">
<h2>Hello Admin!</h2>

<h3>Enter Book Details</h3>

<form action="editBook" method="post">
	<div class="form-group">
		Enter Book Name: 
		<br />
		<input type="text" name="title"
			value="<%=book.getTitle()%>"><br /> <input
			type="hidden" name="bookId" value="<%=book.getBookId()%>">
	</div>
	</select><br> <label for="usr">Choose new authors:</label> 
			<p> Current author ids:<%
	for (Author au:book.getAuthors()){
	%><%=" "+au.getAuthorID()+"," %>
			<%
			}
		%>
		</p>
	<select
		class="selectpicker" data-live-search="true" multiple data-size="1000"
		name="authorId">
		<%
			
			for (Author a : authors) {
				
				if(book.getAuthors().contains(a)){
			%>
			<option selected><%=a.getAuthorID()%>
				<%=a.getAuthorName()%></option>
			
				<%
				}else{
				%>
							<option><%=a.getAuthorID()%>
				<%=a.getAuthorName()%></option>
			
			<%
			}
			%>
		<%
			}
		%>
	</select> <br> <label for="usr">Choose a Genre:</label>

	
	 <select class="selectpicker" data-live-search="true" multiple name="genreId">
		<%
			for (Genre g : genres) {

			if(book.getGenres().contains(g)){
			%>
		<option selected><%=g.getGenreId()%>
			<%=g.getGenreName()%></option>
			
				<%
				}else{
				%>		
		<option><%=g.getGenreId()%>
			<%=g.getGenreName()%></option>
			
			<%
			}

			}
		%>
	</select> <br> <label for="usr">Choose a Publisher:</label> <select
		class="selectpicker" data-live-search="true" name="publisherId">
		<%
			for (Publisher p : publishers) {
				if(book.getPid()== p.getPublisherId()){
					%>
		<option selected><%=p.getPublisherId()%>
			<%=p.getPublisherName()%></option>
					
						<%
						}else{
						%>		
		<option><%=p.getPublisherId()%>
			<%=p.getPublisherName()%></option>					<%
					}

			}
		%>
	</select> <br>
	
	
<button type="submit">Edit Book!</button>
</form>
</div>
<script type="text/javascript" async="" src="./template_files/ga.js"></script>
<script src="./resources/template_files/jquery.min.js"></script>
<script src="./resources/template_files/bootstrap.min.js"></script>
<script src="./resources/template_files/highlight.pack.js"></script>
<script src="./resources/template_files/base.js"></script>
<script src="./resources/template_files/bootstrap-select.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-35848102-1']);
  _gaq.push(['_trackPageview']);

  (function () {
    var ga = document.createElement('script');
    ga.type = 'text/javascript';
    ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(ga, s);
  })();
</script>
</body>
</html>