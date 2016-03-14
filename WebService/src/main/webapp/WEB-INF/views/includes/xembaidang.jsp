<div id="cd-login" align="center">
	<h1>Your Child</h1>
	<%
		int i = 0;
		int j = 0;
	%>
	<c:forEach var="slide" items="${list_user_manage}">
		<%
			i++;
				if (i == 6) {
					i = 1;
					j++;
		%>
		<br></br>
		<div class="slide2">
			<a href="#3rdPage" value="${slide.email}" onclick="dothing(this)"><img
				alt="slide" src="images/AZTECH-ICON-SLIDE-1.0-03.png"
				style="height: 200px; width: 200px;">${slide.name}</a>
		</div>
		<%
			} else {
		%>
		<div class="slide2">
			<a href="#3rdPage" value="${slide.email}" onclick="dothing(this)"><img
				alt="slide" src="images/AZTECH-ICON-SLIDE-1.0-03.png"
				style="height: 200px; width: 200px;"> ${slide.name}</a>
		</div>
		<%
			}
		%>
	</c:forEach>
</div>
<script>
	/* $("#slideClick01").click(function() {
		alert(document.getElementById("slideClick01").alt);
	}); */
	function dothing(element) {
		var href = $(element).attr('value');
		var rootUrl = window.location.origin + "/WebService/";
		$.ajax({
			type : "GET",
			url : rootUrl + "message",
			cache : false,										
			data : 'child='+href,
			success : function(response) {									
				location.reload();
			},
			error : function() {
				$("#message-fail").html(
						"Get data child fail");
			}
		});
	}
</script>