<div id="liveslide" style="width: 92%; margin-left: auto; margin-right: auto;">
	<h1>Map</h1>
	<div style="display: none"><a href="#" id ="map" latitude="${location.latitude}" longitude="${location.longitude}" locate="${location.locate}"></a></div>
		<div id="googleMap" style="width:100%;height:600px;"></div>
</div>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script>
function initialize() {
	var latitude = $("#map").attr('latitude');
	var longitude = $("#map").attr('longitude');
	var locate = $("#map").attr('locate');
	var myCenter=new google.maps.LatLng(latitude,longitude);
	  var mapProp = {
	    center:myCenter,
	    zoom:15,
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	  var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	  var marker=new google.maps.Marker({
		  position:myCenter,
		  });

		marker.setMap(map);

		var infowindow = new google.maps.InfoWindow({
		  content:locate
		  });

		infowindow.open(map,marker);
	}
	google.maps.event.addDomListener(window, 'load', initialize);
</script>