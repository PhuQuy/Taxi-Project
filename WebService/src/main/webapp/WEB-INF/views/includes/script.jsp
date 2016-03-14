<!-- Gem style -->
<script src="js/jquery.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.slimscroll.min.js"></script>

<script type="text/javascript" src="js/jquery.fullPage.js"></script>
<script type="text/javascript" src="js/examples.js"></script>
<script src="js/modernizr.js"></script>
<script src="js/main.js"></script>
<script src="js/Chart.js"></script>
<script src="js/Chart.min.js"></script>


<script>
	$(document)
			.ready(
					function() {
						var rootUrl = window.location.origin + "/WebService/";
						$('#fullpage')
								.fullpage(
										{
											sectionsColor : [ '#1bbc9b',
													'#4BBFC3', '#7BAABE',
													'whitesmoke', '#ccddff' ],
											anchors : [ 'firstPage',
													'secondPage', '3rdPage',
													'4thpage', 'lastPage' ],
											menu : '#menu',
											scrollOverflow : true,
											slidesNavigation : true,

											//equivalent to jQuery `easeOutBack` extracted from http://matthewlein.com/ceaser/
											easingcss3 : 'cubic-bezier(0.175, 0.885, 0.320, 1.275)'
										});

						$('#signup').click(
								function() {
									var roles = [];
									if($('#children-checkbox').attr('checked')) {
										roles.push("Children");
									}
									if($('#parent-checkbox').attr('checked')) {
										roles.push("Parent");
									}
									$.ajax({
										type : "GET",
										url : rootUrl + "/signup",
										cache : false,										
										data : 'userName='
												+ $("#signup-username").val()
												+ '&email='
												+ $("#signup-email").val()
												+ '&password='
												+ $("#signup-password").val()
												+ '&roles=' + JSON.stringify(roles),
										success : function(response) {									
											 if(response.status.statusCode == 2) {												
												$("#signup-username").val("");

												$("#signup-email").val("");

												$("#signup-password").val("");
												$("#message-success").html(
														"Create new user '"
																+ response.data
																+ "' succeed");
											}else if(response.status.statusCode == 0){
												$("#message-fail").html(
												"Error : " + response.status.statusMessage);
											}									 
										},
										error : function() {
											$("#message-fail").html(
													"Can't to create user");
										}
									});
								});
						$('.selected').click(function() {
							$("#message-fail").html("");
							("#message-success").html("");
						});
						$('#upload')
								.click(
										function() {
											$
													.ajax({
														type : "GET",
														url : 'http://localhost:8080/SpringAnotation/upload',
														cache : false,
														data : 'slideName='
																+ $(
																		"#upload-name")
																		.val()
																+ '&link='
																+ $(
																		"#upload-link")
																		.val()
																+ '&voice='
																+ $(
																		"#upload-voice")
																		.val(),
														success : function(
																response) {
															if (response == "OK") {
																$(
																		"#upload-name")
																		.val("");

																$(
																		"#upload-link")
																		.val("");

																$(
																		"#upload-voice")
																		.val("");
															} else {
																alert("can't to create slide");
															}
														},
														error : function() {

														}
													});
										});
					});
</script>