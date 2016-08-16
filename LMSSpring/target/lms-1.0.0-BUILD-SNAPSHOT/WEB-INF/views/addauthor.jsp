<%@page import="com.gcit.lms.entity.Genre"%>
<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%
	AdminService service = new AdminService();
	//Integer count = service.getAuthorCount();
	//int pageNos = 0;
	//Integer pageSize = Integer.valueOf(10);
	List<Book> books = new ArrayList<Book>();
	if (request.getAttribute("authors") != null) {
		//books = (List<Author>) request.getAttribute("authors");
	} else {
		books = service.viewBooks(-1);
	}

%>
<h2>Hello Admin!</h2>
<form action="addAuthor" method="post">
	<div class="form-group">
		<label for="usr">Enter Author Name: <input type="text" name="authorName"><br />
	</div>
<br> <label for="usr">Choose a author:</label> <select
		class="selectpicker" data-live-search="true" multiple data-size="1000"
		name="bookId">
		<%
			for (Book b : books) {
		%>
		<option><%=b.getBookId()%>
			<%=b.getTitle()%></option>
		<%
			}
		%>
	</select>
	<button type="submit">Add Author!</button>
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