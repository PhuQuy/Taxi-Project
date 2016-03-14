package com.spring.dao;

import java.io.IOException;
import java.util.List;

import com.spring.entity.Message;
import com.spring.entity.MessageJson;
import com.spring.entity.User;

public interface MessageDao {
	List<MessageJson> findByUserParent(User user) throws IOException;
	List<MessageJson> findMessessRecieveByUserParent(User user) throws IOException;
	List<MessageJson> findAllMessage() throws IOException;
	List<MessageJson> findMessessSentByUserParent(User user) throws IOException;
	Boolean saveMessage(Message message);
}
