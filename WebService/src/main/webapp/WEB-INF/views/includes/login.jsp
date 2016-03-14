<div id="cd-login" align="center">
	<h1>Login</h1>
	<form class="cd-form" action="<c:url value='/login' />" method='POST'>
		<p class="fieldset">
			<label class="image-replace cd-email" for="signin-email">E-mail</label>
			<input class="full-width has-padding has-border" id="signin-email"
				type="email" placeholder="E-mail" name='email'> <span
				class="cd-error-message">Error message here!</span>
		</p>

		<p class="fieldset">
			<label class="image-replace cd-password" for="signin-password">Password</label>
			<input class="full-width has-padding has-border" id="signin-password"
				type="password" placeholder="Password" name='password'> <a
				href="#secondPage" class="hide-password">Show</a> <span
				class="cd-error-message">Error message here!</span>
		</p>

		<p class="fieldset">
			<input type="checkbox" id="remember-me" checked> <label
				for="remember-me">Remember me</label>
		</p>

		<p class="fieldset">
			<input class="full-width" id="sub" type="submit" value="Login">
		</p>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<p class="cd-form-bottom-message">
		<a href="#secondPage">Forgot your password?</a>
	</p>
	<a href="#secondPage" class="cd-close-form">Close</a>
</div>
<!-- cd-login -->