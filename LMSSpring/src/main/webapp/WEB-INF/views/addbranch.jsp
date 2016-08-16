<%@include file="admin.html" %>
<div align="center">
	<h2>Hello Admin!</h2>
	<h3>Enter Branch Details</h3>
	<form action="addBranch" method="post">
		Enter Branch Name: <input type="text" name="branchName"><br />
		Enter Branch Address: <input type="text" name="branchAddress"><br />
		<button type="submit">Add Branch!</button>
	</form>
</div>