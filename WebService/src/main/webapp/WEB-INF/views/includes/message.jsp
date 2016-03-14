<div id="liveslide" style="width: 92%; margin-left: auto; margin-right: auto;">
	<h1>Message</h1>
		<c:forEach var="slide" items="${list_message_recieve}">
			<div class="slide2">
				<a href="#3rdPage" value="${slide.content}" style="color: #25393C"
					onclick="showslide(this)"><img alt="slide"
					src="images/messagesIcon.png"
					style="height: 200px; width: 200px;">From : ${slide.phone} </br>Date : ${slide.sent_date}</a>
			</div>
		</c:forEach>
		<c:forEach var="slide" items="${list_message_send}">
			<div class="slide2">
				<a href="#3rdPage" value="${slide.content}" style="color: #25393C"
					onclick="showslide(this)"><img alt="slide"
				src="images/messagesIcon.png" style="height: 200px; width: 200px;">To : ${slide.phone} </br>Date : ${slide.sent_date}</a>
			</div>
		</c:forEach>
</div>
<div align="center" id="popup" style="display: none;">

	<iframe id="frame" width="810" height="620" 
		src="#"></iframe>
	<input type="button" id="off-popup" value="Off popup"">
</div>
<script>
	function showslide(element) {
		var href = $(element).attr('value');
		
		alert(href);	  
	}
</script>