<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Web Service</title>
<meta name="author" content="Alvaro Trigo Lopez" />
<meta name="description" content="fullPage demo easing effect." />
<meta name="keywords"
	content="fullpage,jquery,demo,easing,effect,jqueui" />
<meta name="Resource-type" content="Document" />


<%@include file="includes/script.jsp"%>
<%@include file="includes/css.jsp"%>
</head>
<body>
	<ul id="menu">
		<li data-menuanchor="firstPage"><a href="#firstPage"> Home</a></li>
		<sec:authorize access="isAnonymous()">

			<li data-menuanchor="secondPage"><a href="#secondPage"
				class="login1">Login</a></li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<li data-menuanchor="secondPage"><a href="#secondPage">Logout</a></li>
		</sec:authorize>
		<li data-menuanchor="3rdPage"><a href="#3rdPage">Lesson</a></li>
	</ul>

	<div id="fullpage">
		<div class="section " id="section0">

			<div class="slide" id="slide1" data-anchor="slide1">
				<h1 style="padding-top: 100px">Ideas Worth Spreading</h1>
				<p>Learning and Study</p>
				<img src="images/Debt-Recovery-Case-Study.png" alt="fullPage" />
			</div>

			<div class="slide" id="slide2" data-anchor="slide2">
				<h1 style="padding-top: 100px">Icon</h1>
				<p>Powerpoint Online</p>
				<img src="images/crab.png" alt="crab" />
			</div>

			<div class="slide" id="slide3" data-anchor="slide3">
				<h1 style="padding-top: 100px">Great environment</h1>
				<p>Student</p>
				<img src="images/teacher_student.png" alt="crab" />
			</div>
		</div>

		<div class="section" id="section1">
			<div class="intro">
				<div class="cd-gallery-container">
					<nav class="cd-filter">
					<ul>
						<sec:authorize access="isAnonymous()">
							<li><a class="selected" data-type="red" href="#0">Login</a></li>

							<li><a data-type="blue" href="#0">Sign up</a></li>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<li><a class="selected" data-type="red" href="#0">Children</a></li>

							<li><a data-type="blue" href="#0">Parent</a></li>
						</sec:authorize>
					</ul>
					</nav>

					<ul class="cd-gallery cd-container">
						<li>
							<ul class="cd-item-wrapper">
								<sec:authorize access="isAnonymous()">
									<li data-type="red" class="is-visible"><%@include
											file="includes/login.jsp"%></li>
									<li data-type="blue" class="is-hidden"><%@include
											file="includes/signup.jsp"%></li>
								</sec:authorize>
								<sec:authorize access="isAuthenticated()">
									<li data-type="red" class="is-visible"><%@include
											file="includes/xembaidang.jsp"%></li>

									<li data-type="blue" class="is-hidden"><%@include
											file="includes/dangbai.jsp"%></li>
								</sec:authorize>
							</ul> <!-- cd-gallery -->
						</li>
					</ul>
				</div>
				<!-- cd-gallery-container -->

			</div>
		</div>
		<div class="section" id="section2">
			<div class="intro">
				<div class="slide" id="slide1" data-anchor="slide1">
					<%@include file="includes/message.jsp"%>
				</div>

				<div class="slide" id="slide2" data-anchor="slide2">
					<%@include file="includes/call_log.jsp"%>
				</div>

				<div class="slide" id="slide3" data-anchor="slide3">
					<%@include file="includes/map.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>