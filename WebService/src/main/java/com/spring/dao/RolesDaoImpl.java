package com.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Roles;
import com.spring.entity.User;

@Repository
public class RolesDaoImpl implements RolesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public Roles findRoleByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Roles.class);
		criteria.add(Restrictions.eq("name", name));
		return (Roles) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Roles> findByUser(User user) {
		 return (List<Roles>) sessionFactory.getCurrentSession().createQuery("select r from Roles r inner join r.users u Where u = :user").setParameter("user", user).list();
	}
}
