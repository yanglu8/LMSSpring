<%@include file="admin.html" %>
	<h2>Hello Admin!</h2>
	<h3>Enter Publisher Details</h3>
	<form action="addPublisher" method="post">
		Enter Publisher Name: <input type="text" name="publisherName"><br />
		Enter Publisher Address: <input type="text" name="publisherAddress"><br />
		Enter Publisher Phone: <input type="text" name="publisherPhone"><br />
		<button type="submit">Add Publisher!</button>
	</form>
