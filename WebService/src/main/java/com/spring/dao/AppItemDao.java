package com.spring.dao;

import java.util.List;

import com.spring.entity.AppItem;
import com.spring.entity.User;

public interface AppItemDao {
	AppItem findById(int id);
	Boolean saveApp(AppItem app);
	
	Boolean update(AppItem app);
	List<AppItem> getAppsByOwner(User user);
	
	List<AppItem> getAppsLockByOwner(User user);
	
	List<AppItem> getAppsLockByManager(User user);
}
