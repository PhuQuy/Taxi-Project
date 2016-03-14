<canvas id="buyers" width="600" height="400"></canvas>
<script>
		// line chart data
		var text = '{ "dataline" : [' + '{ "data":65},' + '{ "data":59},'
				+ '{ "data":80},' + '{ "data":81},' + '{ "data":56},'
				+ '{ "data":55},' + '{ "data":40} ]}';
		//    var data1=[65,59,80,81,56,55,40]    ;
		var data1 = JSON.parse(text);

		var buyerData = {
			labels : [ "January", "February", "March", "April", "May", "June",
					"July" ],
			datasets : [
					{
						label : "My First dataset",
						fillColor : "rgba(220,220,220,0.2)",
						strokeColor : "rgba(220,220,220,1)",
						pointColor : "rgba(220,220,220,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(220,220,220,1)",
						data : [ data1.dataline[0].data,
								data1.dataline[1].data, data1.dataline[2].data,
								data1.dataline[3].data, data1.dataline[4].data,
								data1.dataline[5].data, data1.dataline[6].data ]
					}, {
						label : "My Second dataset",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ 28, 48, 40, 19, 86, 27, 90 ]
					} ]
		}
		// get line chart canvas
		var buyers = document.getElementById('buyers').getContext('2d');
		// draw line chart
		new Chart(buyers).Line(buyerData);
	</script>