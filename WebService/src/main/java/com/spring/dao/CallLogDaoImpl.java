package com.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.CallLog;
import com.spring.entity.User;

@Repository
public class CallLogDaoImpl implements CallLogDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userService;
	
	@Transactional
	@Override
	public CallLog findById(int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CallLog.class);
		criteria.add(Restrictions.eq("id", id));
		return (CallLog) criteria.uniqueResult();
	}

	@Transactional
	@Override
	public Boolean saveCallLog(CallLog callLog) {
		try {
			sessionFactory.getCurrentSession().save(callLog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public Boolean update(CallLog callLog) {
		try {
			sessionFactory.getCurrentSession().update(callLog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<CallLog> getCallLogByUser(User user) {
		return (List<CallLog>) sessionFactory.getCurrentSession().createQuery("select c from CallLog c Where c.owner = :user and c.isRead = 0").setParameter("user", user).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<CallLog> getCallLogByManager(User user) {
		List<User> users = userService.findManageByUser(user);
		return (List<CallLog>) sessionFactory.getCurrentSession().createQuery("select c from CallLog c Where c.owner in :users and c.isRead = 0").setParameter("users", users).list();
	}

	
}
