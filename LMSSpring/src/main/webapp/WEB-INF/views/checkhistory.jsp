<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%
	ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	BorrowerService service = (BorrowerService) ac.getBean("borrowerService");
	Integer cardNo =Integer.parseInt(request.getParameter("cardNo"));
	List<BookLoans> books = new ArrayList<BookLoans>();
	books = service.viewHistoryLoans(cardNo);
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
			<th>BookId</th>
			<th>Title</th>
			<th>Branch</th>
			<th>Due Date</th>
			<th>Return Date</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
				<%
					for (BookLoans b : books) {
				%>
				<tr>
					<td><%=b.getBookId()%></td>
					<td><%=b.getTitle()%></td>
					<td><%=b.getBranchName()%></td>
					<td><%=b.getDueDate()%></td>
					<td><%=b.getDateIn()%></td>
					
					<%
					String returndate = b.getDateIn();
					String duedate = b.getDueDate();
					String dueyear = duedate.split(" ")[0].split("-")[0];
					String duemonth = duedate.split(" ")[0].split("-")[1];
					String dueday = duedate.split(" ")[0].split("-")[2];
					String returnyear = returndate.split(" ")[0].split("-")[0];
					String returnmonth = returndate.split(" ")[0].split("-")[1];
					String returnday = returndate.split(" ")[0].split("-")[2];
					if (Integer.parseInt(dueyear)>Integer.parseInt(returnyear)){
						%>
						<td><button name="Edit" class="btn btn-sm btn-success" 
							onclick="javascript:location.href='#'">Return</button></td>
						<% 
					}else if(Integer.parseInt(dueyear)<Integer.parseInt(returnyear)){
						%>
						<td><button name="Edit" class="btn btn-sm btn-danger" 
							onclick="javascript:location.href='#'">Return</button></td>
						<% 
					}else if(Integer.parseInt(dueyear)==Integer.parseInt(returnyear)){
						if (Integer.parseInt(duemonth)>Integer.parseInt(returnmonth))
						{
							%>
						<td><button name="Edit" class="btn btn-sm btn-success" 
							onclick="javascript:location.href='#'">View History</button></td>
							<% 
						}else if (Integer.parseInt(duemonth)<Integer.parseInt(returnmonth))
						{
							%>
						<td><button name="Edit" class="btn btn-sm btn-danger" 
							onclick="javascript:location.href='#'">Pay Fine</button></td>
							<% 
						}else if (Integer.parseInt(duemonth)==Integer.parseInt(returnmonth)){
								if (Integer.parseInt(dueday)>Integer.parseInt(returnday))
								{
									%>
								<td><button name="Edit" class="btn btn-sm btn-success" 
									onclick="javascript:location.href='#'">View History</button></td>
									<% 
								}else if (Integer.parseInt(duemonth)<Integer.parseInt(returnday))
								{
									%>
								<td><button name="Edit" class="btn btn-sm btn-danger" 
									onclick="javascript:location.href='#'">Pay Fine</button></td>
									<% 
								}else if (Integer.parseInt(duemonth)==Integer.parseInt(returnday)){
									%>
								<td><button name="Edit" class="btn btn-sm btn-success" 
									onclick="javascript:location.href='#'">View History</button></td>
									<% 
								}
						}
						
						
						
						
						
						
						
						
						
						%>
						
						<% 
					}
					%>
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