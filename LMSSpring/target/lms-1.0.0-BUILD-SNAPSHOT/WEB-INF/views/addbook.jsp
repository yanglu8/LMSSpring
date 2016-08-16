<%@page import="com.gcit.lms.entity.Genre"%>
<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@ page import="com.gcit.lms.entity.Genre"%>
<%
	AdminService service = new AdminService();
	//Integer count = service.getAuthorCount();
	//int pageNos = 0;
	//Integer pageSize = Integer.valueOf(10);
	List<Author> authors = new ArrayList<Author>();
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = service.viewAuthors(-1);
	}
	
	List<Publisher> publishers = new ArrayList<Publisher>();
	if(request.getAttribute("pulishers")!=null){
		publishers = (List<Publisher>)request.getAttribute("pulishers");
	}else{
		publishers = service.viewPublishers(-1);	
	}
	List<Genre> genres = new ArrayList<Genre>();
	if(request.getAttribute("genres")!=null){
		genres = (List<Genre>)request.getAttribute("genres");
	}else{
		genres = service.viewGenres(-1);	
	}
%>
<h2>Hello Admin!</h2>

<h3>Enter Book Details</h3>

<form action="addBook" method="post">
	<div class="form-group">
		<label for="usr">Title:</label> <input type="text"
			class="form-control" name="booktitle">
	</div>
<br> <label for="usr">Choose a author:</label> <select
		class="selectpicker" data-live-search="true" multiple data-size="1000"
		name="authorId">
		<%
			for (Author a : authors) {
		%>
		<option><%=a.getAuthorID()%>
			<%=a.getAuthorName()%></option>
		<%
			}
		%>
	</select> <br> <label for="usr">Choose a Genre:</label> <select class="selectpicker" data-live-search="true" multiple name="genreId">
		<%
			for (Genre g : genres) {
		%>
		<option><%=g.getGenreId()%>
			<%=g.getGenreName()%></option>
		<%
			}
		%>
	</select> <br> <label for="usr">Choose a Publisher:</label> <select
		class="selectpicker" data-live-search="true" name="publisherId">
		<%
			for (Publisher p : publishers) {
		%>
		<option><%=p.getPublisherId()%>
			<%=p.getPublisherName()%></option>
		<%
			}
		%>
	</select> <br>
	<button type="submit">Add Book!</button>
	<div></div>
</form>
<script type="text/javascript" async="" src="./template_files/ga.js"></script>
<script src="./template_files/jquery.min.js"></script>
<script src="./template_files/bootstrap.min.js"></script>
<script src="./template_files/highlight.pack.js"></script>
<script src="./template_files/base.js"></script>
<script src="./template_files/bootstrap-select.min.js"></script>
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