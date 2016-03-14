package com.spring.dao;

import java.util.List;

import com.spring.entity.User;
public interface UserDao {
	User findByUserEmail(String email);
	boolean addUser(User ur);
	User updateUser (User ur);
	List<User> findManageByUser(User user);
	List<User> findParent(User user);
}
