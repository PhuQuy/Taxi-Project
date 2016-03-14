<div id="cd-signup" align="center">
	<h3 id="message-success" style="color: #25393C"></h3>
	<h3 id="message-fail" style="color: #cc3333;"></h3>
	<h1>Sign Up</h1>
	<!-- sign up form -->
	<form class="cd-form" autocomplete="off">
		<p class="fieldset">
			<label class="image-replace cd-username" for="signup-username">Username</label>
			<input class="full-width has-padding has-border" id="signup-username"
				type="text" placeholder="Username"> <span
				class="cd-error-message">Error message here!</span>
		</p>

		<p class="fieldset">
			<label class="image-replace cd-email" for="signup-email">E-mail</label>
			<input class="full-width has-padding has-border" id="signup-email"
				type="email" placeholder="E-mail"> <span
				class="cd-error-message">Error message here!</span>
		</p>

		<p class="fieldset">
			<label class="image-replace cd-password" for="signup-password">Password</label>
			<input class="full-width has-padding has-border" id="signup-password"
				type="password" placeholder="Password"> <a
				href="#secondPage" class="hide-password">Show</a> <span
				class="cd-error-message">Error message here!</span>
		</p>
		<p class="fieldset">
			<input type="checkbox" id="children-checkbox"> <label
				for="children-checkbox">Children</label>
			<input type="checkbox" id="parent-checkbox" style="margin-left: 50px"> <label
				for="parent-checkbox">Parent</label>
		</p>
		<p class="fieldset">
			<input type="checkbox" id="accept-terms"> <label
				for="accept-terms">I agree to the <a href="#0">Terms</a></label>
		</p>

		<p class="fieldset">
			<input class="full-width has-padding" type="button" id="signup"
				value="Create account">
		</p>
	</form>

	<!-- <a href="#0" class="cd-close-form">Close</a> -->
</div>
<!-- cd-signup -->