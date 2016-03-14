package com.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Map;
import com.spring.entity.MapJson;
import com.spring.entity.User;

@Repository
public class MapDaoImpl implements MapDao{
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public Map findById(int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Map.class);
		criteria.add(Restrictions.eq("id", id));
		return (Map) criteria.uniqueResult();
	}

	@Transactional
	@Override
	public Boolean saveMap(Map map) {
		try {
			sessionFactory.getCurrentSession().save(map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public Boolean update(Map map) {
		try {
			sessionFactory.getCurrentSession().update(map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Map> getMapsByOwner(User user) {
		return (List<Map>) sessionFactory.getCurrentSession().createQuery("select m from Map m Where m.owner = :user").setParameter("user", user).list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MapJson> getMapJsonsByOwner(User user) {
		List<Map> maps = (List<Map>) sessionFactory.getCurrentSession().createQuery("select m from Map m Where m.owner = :user").setParameter("user", user).list();
		List<MapJson> mapJsons = new ArrayList<MapJson>();
		for(Map m : maps) {
			MapJson mapJson = new MapJson();
			mapJson.setId(m.getId());
			mapJson.setLatitude(m.getLatitude());
			mapJson.setLocate(m.getLocate());
			mapJson.setLongitude(m.getLongitude());
			mapJson.setUserName(m.getOwner().getName());
			mapJsons.add(mapJson);
		}
		return mapJsons;
	}

}
