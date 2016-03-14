package com.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public User findByUserEmail(String email) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		return (User) criteria.uniqueResult();
	}

	@Transactional
	@Override
	public boolean addUser(User ur) {
		try {
			sessionFactory.getCurrentSession().save(ur);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public User updateUser(User ur) {
		try {
			User user = ur;
			sessionFactory.getCurrentSession().update(ur);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<User> findManageByUser(User user) {
		 return (List<User>) sessionFactory.getCurrentSession().createQuery("select u.manage from User u Where u = :user").setParameter("user", user).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<User> findParent(User user) {
		try {
		return (List<User>) sessionFactory.getCurrentSession().createQuery("select u from User u Where u.manage = :user").setParameter("user", user).list();
		}catch (Exception e) {
			return new ArrayList<User>();
		}
	}
}
