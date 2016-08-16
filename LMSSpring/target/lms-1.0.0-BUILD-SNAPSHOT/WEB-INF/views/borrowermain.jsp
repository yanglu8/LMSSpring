<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%
	BorrowerService service = new BorrowerService();
	Integer count;
	String cardNo = request.getParameter("cardNo");
	Object action= request.getAttribute("action");
	
	if (request.getAttribute("action")!=null){
		
	}
	System.out.println("action: "+action);
%>
	<%if ((Integer)(action)==1){
		%>
	<div class="alert alert-success">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>Success! </strong>   You Just Checked A BoOk!
	</div>
		<%
	} %>
		<%if ((Integer)(action)==0){
		%>
  <div class="alert alert-success">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <strong>Success!</strong> You Just Returned A BoOk!
  </div>		
  <%
	} %>

<div class="container">
	<section style="padding-top: 40px;">
		<div class="row" style="padding-bottom: 30px;">
			<div class="col-md-12 text-center">
				<h2>Choose Your Role Below to Continue</h2>
			</div>
		</div>
		<!-- ROW END -->

		<div class="row">
		<a href="checkoutbook.jsp?cardNo=<%=cardNo%>">
			<div class="col-md-6 portfolio-image">
				<img  src="./img/checkbook.jpg" class="img-responsive" />
				<h3>   Check A BoOk</h3>
				<h5></h5>
			</div>
		</a>
		<a href="returnbook.jsp?cardNo=<%=cardNo%>">
			<div class="col-md-6 portfolio-image">
				<img src="./img/returnbook.jpg" class="img-responsive" />
				<h3> Return A Book</h3>
				<h5></h5>
			</div>
		</a>
		</div>
		<!-- ROW END -->
	</section>
	<!-- SECTION END -->
</div>
<!-- CONATINER END -->