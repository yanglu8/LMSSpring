<%@include file="include.html"%>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
%>
<form action="varifyCardReserve" method="get" name ="varify" onsubmit="return validateForm()">
<div class="container">
    <div class="row">
        <div class="col-md-offset-5 col-md-3">
            <div class="form-login">
            <h4>Please login to continue</h4>
            <input type="text" id="cardNo" class="form-control input-sm chat-input" placeholder="cardNo" name="cardNo"/>
            <input type="hidden" name="branchId" value=<%=branchId %>>
            <input type="hidden" name="bookId" value=<%=bookId %>>
            </br>
            <div class="wrapper">
			<button type="submit">Enter!</button>
            </div>
            </div>
        </div>
    </div>
</div>
	</form>
<script type="text/javascript">
	function validateForm() {
	    var x = document.forms["varify"]["cardNo"].value;
	    var isnum = /^\d+$/.test(x);
	    if (isnum == false ) {
	        alert("card number must be number!!");
	        return false;
	    }
	}
</script>