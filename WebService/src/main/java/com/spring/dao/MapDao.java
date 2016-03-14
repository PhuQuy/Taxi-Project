package com.spring.dao;

import java.util.List;

import com.spring.entity.Map;
import com.spring.entity.MapJson;
import com.spring.entity.User;

public interface MapDao {
	Map findById(int id);
	Boolean saveMap(Map map);
	
	Boolean update(Map map);
	List<Map> getMapsByOwner(User user);
	List<MapJson> getMapJsonsByOwner(User user);
}
