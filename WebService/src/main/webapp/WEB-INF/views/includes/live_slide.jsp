<div id="liveslide" style="width: 92%; margin-left: auto; margin-right: auto;">
	<h1>Live Slide</h1>
	<c:forEach var="slide" items="${listSlide}">
		<div class="slide2">
			<a href="#3rdPage" value="${slide.link}" onclick="showslide(this)"><img
				alt="slide" src="images/icon-store-presentation.png"
				style="height: 200px; width: 200px;"> ${slide.name}</a>
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
	 	/* $('#frame').attr("src",href); */
	 	document.getElementById('frame').src="#";
	 	document.getElementById('frame').src=href;
		 $('#liveslide').hide();
		$('#popup').fadeIn('slow', 'swing');	  
	}
/* 	$('#show-popup').click(function() {
		$(this).hide();
		
	}); */
	$('#off-popup').click(function() {
		$('#popup').fadeOut('slow', 'swing');
		$('#liveslide').show();
	});
</script>