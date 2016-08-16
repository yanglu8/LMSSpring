<%@include file="admin.html" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AdminService service = (AdminService) ac.getBean("adminService");
	Integer count;
	//String search = request.getParameter("searchPublishers");
	List<Publisher> publishers = new ArrayList<Publisher>();
	if(request.getAttribute("publishers")!=null){
		publishers = (List<Publisher>)request.getAttribute("publishers");
	}else{
		publishers = service.viewPublishers(1);	
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
<table id="myTable" class="table table-striped">
	<thead>
		<tr>
			<th>PublisherId</th>
			<th>Title</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (Publisher a : publishers) {
				%>
				<tr>
					<td><%=a.getPublisherId()%></td>
					<td><%=a.getPublisherName()%></td>
					<td><button name="Edit" class="btn btn-sm btn-success" data-toggle="modal" data-target="#myModal"
							href='editpublisher.jsp?publisherId=<%=a.getPublisherId()%>&publisherName=<%=a.getPublisherName()%>&publisherAddress=<%=a.getPublisherAddress()%>&publisherPhone=<%=a.getPublisherPhone()%>'>Edit</button></td>
					<td><button name="Delete" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deletePublisher?publisherId=<%=a.getPublisherId()%>'">Delete</button></td>
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