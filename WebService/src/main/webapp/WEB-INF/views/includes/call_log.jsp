<div id="liveslide"
	style="width: 92%; margin-left: auto; margin-right: auto;">
	<h1>Call Log</h1>
	<c:forEach var="call" items="${phone}">
		<div class="slide2">
			<a href="#" value="${call.duration}" style="color: #25393C"
				onclick="showslide(this)"><img alt="slide"
				src="images/Phone-icon.png" style="height: 200px; width: 200px;">
			<c:if test="${call.callType == 1}">From
  </c:if>
				<c:if test="${call.callType == 2}">To
  </c:if>
				<c:if test="${call.callType == 3}">Miss
  </c:if> : ${call.phoneCall} </br>Date : ${call.dateCall}</a>
		</div>
	</c:forEach>
</div>
<script>
	function showslide(element) {
		var href = $(element).attr('value');

		alert(href);
	}
</script>