package com.spring.dao;

import java.util.List;

import com.spring.entity.CallLog;
import com.spring.entity.User;

public interface CallLogDao {
	CallLog findById(int id);
	Boolean saveCallLog(CallLog callLog);
	
	Boolean update(CallLog callLog);
	
	List<CallLog> getCallLogByUser(User user);
	List<CallLog> getCallLogByManager(User user);
}
