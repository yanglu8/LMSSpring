<%@include file="librarian.html"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Integer count;
	//String search = request.getParameter("searchAuthors");
	String branchId = request.getParameter("branchId");
	if(request.getAttribute("branchId")!=null){
		(branchId) = request.getAttribute("branchId").toString();
	}
	
	Object action = null;
	if(request.getAttribute("action")!=null){
		action = request.getAttribute("action");
	}
%>

	<%
	if(action!=null){
	%>
			<%if (action.equals("add")){
			%>
		<div class="alert alert-success">
	    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    <strong>Success!  You Just Add a Copy!!</strong>   
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
	    <strong>Success! You Just Edit a Branch!</strong> 
	  </div>
			<%
		} %>
		
	<%
	}
	%>


<div>
	<div align="center">
	<h2>
		Choose your action.
	</h2>
  <a href="editbranch?branchId=<%=branchId%>"><button type="button" class="btn btn-primary btn-lg">Edit Branch</button></a>
<br><br><br>
  <a href="addcopies?branchId=<%=branchId%>"> <button type="button" class="btn btn-primary btn-lg">Add Copies to Branch</button>
    </div>