<%@include file="include.html"%>
<jsp:include page="./css/index.css" />
<!-- Main jumbotron for a primary marketing message or call to action -->
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%@ page import="com.gcit.lms.entity.Branch"%>
<%
	AdminService service = new AdminService();
	Integer count;
	//String search = request.getParameter("searchBranches");
	List<Branch> branches = new ArrayList<Branch>();
	branches = service.viewBranches(1);
%>

<div class="container">
	<div class="container">
		<h2>Let's DO something</h2>
		<ul class="nav nav-tabs">
			<li class="active"><a href="#home">Everything</a></li>
			<li><a href="#menu1">Articles</a></li>
			<li><a href="#menu2">Authors</a></li>
			<li><a href="#menu3">Media</a></li>
		</ul>

		<div class="tab-content">
			<div id="home" class="tab-pane fade in active">
				<h3>
					<a href="borrower.jsp">Click Here. try borrow a book!</a>
				</h3>
			<div class="tab-pane active" id="everythingTab">
				<div class="panel-body" id="search-panel-body">
					<div class="row">
						<div class="col-sm-6">
							<h3>
								<span class="fa fa-search"></span> Easy Search
							</h3>
						</div>
					</div>
					<div class="row">
						<form class="form-inline" role="form" method="get"
							action="http://search.grainger.uiuc.edu/discovery/splitsearch.asp"
							id="search_everything">
							<label for="keyword" class="sr-only">Enter search terms</label> <input
								type="text" class="form-control easy-search-text-input"
								id="keyword" name="searcharg" maxlength="255"
								placeholder="Enter search terms" onfocus="this.placeholder = ''"
								onblur="this.placeholder = 'Enter search terms'"
								aria-required="true" /> <label for="dropdown" class="sr-only">Subject
								Selection</label> <select id="dropdown"
								class="form-control easy-search-select" name="selection"
								aria-required="true">
								<option value="gen">Multi-Subject Resources</option>
								<option value="news">Current News Sources</option>

							</select> <label class="sr-only"
								for="easy-search-all-full">Search</label> <input
								class="btn btn-primary" type="submit" alt="submit"
								value="Search" name="searchbutton">
						</form>
					</div>
				</div>
			</div>
				<p></p>
			</div>
			<div id="menu1" class="tab-pane fade">
				<h3>Click Here. try borrow a book!</h3>
				<p></p>
			</div>
			<div id="menu2" class="tab-pane fade">
				<h3>Click Here. It will take you to search everything</h3>
				<p></p>
			</div>
			<div id="menu3" class="tab-pane fade">
				<h3>Click Here. It will take you to search everything</h3>
				<p></p>
			</div>



			<script>
				$(document).ready(function() {
					$(".nav-tabs a").click(function() {
						$(this).tab('show');
					});
				});
			</script>
		</div>

		<br>
		<h2>Check Out New BOoks</h2>
		<div class="bookgallary">
			<br>
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
					<li data-target="#myCarousel" data-slide-to="3"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="./img/books.jpg" alt="book1" width="460" height="345">
					</div>

					<div class="item">
						<img src="./img/books.jpg" alt="book2" width="460" height="345">
					</div>
					<div class="item">
						<img src="./img/books.jpg" alt="book3" width="460" height="345">
					</div>
				</div>

				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel" role="button"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel" role="button"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<br>
		<br>
		<br>
		<h2>Library Hours and Locations</h2>
		<div class="col-md-4">
        <label for="search-field" class="sr-only">Filter libraries by name</label>
        <input style="width:100%;" type="text" class="form-control" id="search-field" placeholder="Filter libraries by name" aria-required="false"/>
        </div>
		<div class="container">
			<a href="" class="list-group-item active">
				<h3 class="list-group-item-heading">We have different branch
					all over the country!!</h3>

			</a>


			<%
					for (Branch b : branches) {
				%>
			<a href=<%="realviewbranch?branchId=" +b.getBranchId()%>
				class="list-group-item">
				<h4 class="list-group-item-heading">
					BranchName:
					<%=b.getBranchName()%></h4>
				<p class="list-group-item-text">
					Location:
					<%=b.getBranchAddress()%></p>
			</a>
			<%
					}
				%>
		</div>