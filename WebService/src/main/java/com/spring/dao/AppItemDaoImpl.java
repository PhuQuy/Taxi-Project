package com.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.AppItem;
import com.spring.entity.User;

@Repository
public class AppItemDaoImpl implements AppItemDao{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDao userService;
	
	@Transactional
	@Override
	public Boolean saveApp(AppItem app) {
		try {
			sessionFactory.getCurrentSession().save(app);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<AppItem> getAppsByOwner(User user) {	
		return (List<AppItem>) sessionFactory.getCurrentSession().createQuery("select a from AppItem a Where a.owner = :user").setParameter("user", user).list();
	}

	@Transactional
	@Override
	public AppItem findById(int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppItem.class);
		criteria.add(Restrictions.eq("id", id));
		return (AppItem) criteria.uniqueResult();
	}

	@Transactional
	@Override
	public Boolean update(AppItem app) {
		try {
			sessionFactory.getCurrentSession().update(app);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<AppItem> getAppsLockByOwner(User user) {	
		return (List<AppItem>) sessionFactory.getCurrentSession().createQuery("select a from AppItem a Where a.owner = :user and a.included = 1").setParameter("user", user).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<AppItem> getAppsLockByManager(User user) {	
		List<User> users = userService.findManageByUser(user);
		return (List<AppItem>) sessionFactory.getCurrentSession().createQuery("select a from AppItem a Where a.owner in :users").setParameter("users", users).list();
	}

}
