package com.spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.UserDao;
import com.spring.entity.Roles;


@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
	
		com.spring.entity.User user = userDao.findByUserEmail(email);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoleses());

		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.spring.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPassword(), user.isEnable(), user.isEnable(), user.isEnable(), user.isEnable(), authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Roles> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (Roles userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}