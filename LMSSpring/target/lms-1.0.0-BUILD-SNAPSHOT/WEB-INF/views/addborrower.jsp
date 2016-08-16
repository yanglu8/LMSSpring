<%@include file="admin.html" %>
	<h2>Hello Admin!</h2>
	<h3>Enter Borrower Details</h3>
	<form action="addBorrower" method="post">
		Enter Borrower Name: <input type="text" name="borrowerName"><br />
		Enter Borrower Address: <input type="text" name="borrowerAddress"><br />
		Enter Borrower Phone: <input type="text" name="borrowerPhone"><br />
		<button type="submit">Add Borrower!</button>
	</form>
