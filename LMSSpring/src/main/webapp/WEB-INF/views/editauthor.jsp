<%@page import="com.gcit.lms.entity.Genre"%>
<%@ include file="admin.html" %>
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
	Integer authorID = Integer.parseInt(request.getParameter("authorId"));
	Author author = null;
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	author = service.viewAuthorByID(authorID);
	List<Book> books = new ArrayList<Book>();
	if(request.getAttribute("genres")!=null){
		//books = (List<Genre>)request.getAttribute("genres");
	}else{
		books = service.viewBooks(1);	
	}
%>
<div align="center">
	<h3>Enter Author Details to Edit</h3>
	<form action="editAuthor" method="post">
		Enter Author Name: 
		<br/>
		<input type="text" name="authorName"
			value="<%=author.getAuthorName()%>"><br /> <input
			type="hidden" name="authorId" value="<%=author.getAuthorID()%>">
		<p>
		Select the books
		</p>
		<select class="selectpicker" data-live-search="true" multiple name="bookId">
		<%
			for (Book b : books) {
				if (author.getBooks().contains(b)){
		%>
		<option selected><%=b.getBookId()%>
			<%=b.getTitle()%></option>
		<%
				}else{
					%>
			<option ><%=b.getBookId()%>
			<%=b.getTitle()%></option>
					<%
				}
					}
		%>
	</select>
		<button type="submit">Edit Author!</button>
	</form>
</div>


<script type="text/javascript" async="" src="./resources/template_files/ga.js"></script>
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