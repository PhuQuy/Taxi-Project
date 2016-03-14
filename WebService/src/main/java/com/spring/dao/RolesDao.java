package com.spring.dao;

import java.util.List;

import com.spring.entity.Roles;
import com.spring.entity.User;

public interface RolesDao {
	Roles findRoleByName(String name);
	
	List<Roles> findByUser(User user);
}
