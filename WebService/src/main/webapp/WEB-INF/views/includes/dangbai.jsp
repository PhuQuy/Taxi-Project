<div id="cd-signup" align="center">
	<h1>Your Parent</h1>
	<c:forEach var="parent" items="${your_parent}">
			 ${parent.name} </br>
			 ${parent.email}</br>
		</c:forEach>
</div>