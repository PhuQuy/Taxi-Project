package com.spring.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Message;
import com.spring.entity.MessageJson;
import com.spring.entity.User;
import com.spring.service.Util;

@Repository
public class MessageDaoImpl implements MessageDao{
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public Boolean saveMessage(Message message) {
		try {
			sessionFactory.getCurrentSession().save(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MessageJson> findByUserParent(User user) throws IOException {
		List<MessageJson> messageJsons = new ArrayList<MessageJson>();
		 List<Message> messages =  (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where (m.reciever = :user or m.sender = :user) and m.isRead = 0").setParameter("user", user).list();
		 for(Message m : messages) {
			 MessageJson messageJson = new MessageJson();
			 messageJson.setContent(Util.decompress(m.getContent()));
			 messageJson.setIsRead(m.getIsRead());
			 messageJson.setName(m.getName());
			 messageJson.setNameReciever(m.getReciever().getName());
			 messageJson.setNameSender(m.getSender().getName());
			 messageJson.setSent_date(m.getSent_date());
			 messageJson.setPhone(m.getPhone());
			 messageJsons.add(messageJson);
			// m.setIsRead(Boolean.TRUE);
		 }
		 return messageJsons;
	//	return (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where (m.reciever = :user or m.sender = :user) and m.isRead = 0").setParameter("user", user).list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MessageJson> findMessessSentByUserParent(User user) throws IOException {
		List<MessageJson> messageJsons = new ArrayList<MessageJson>();
		 List<Message> messages =  (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where  m.sender = :user and m.isRead = 0").setParameter("user", user).list();
		 for(Message m : messages) {
			 MessageJson messageJson = new MessageJson();
			 messageJson.setContent(Util.decompress(m.getContent()));
			 messageJson.setName(m.getName());
			 messageJson.setIsRead(m.getIsRead());
			 messageJson.setNameReciever(m.getReciever().getName());
			 messageJson.setNameSender(m.getSender().getName());
			 messageJson.setSent_date(m.getSent_date());
			 messageJson.setPhone(m.getPhone());
			 messageJsons.add(messageJson);
			// m.setIsRead(Boolean.TRUE);
		 }
		 return messageJsons;
	//	return (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where (m.reciever = :user or m.sender = :user) and m.isRead = 0").setParameter("user", user).list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MessageJson> findMessessRecieveByUserParent(User user) throws IOException{
		List<MessageJson> messageJsons = new ArrayList<MessageJson>();
		 List<Message> messages =  (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where m.reciever = :user and m.isRead = 0").setParameter("user", user).list();
		 for(Message m : messages) {
			 MessageJson messageJson = new MessageJson();
			 messageJson.setContent(Util.decompress(m.getContent()));
			 messageJson.setIsRead(m.getIsRead());
			 messageJson.setName(m.getName());
			 messageJson.setNameReciever(m.getReciever().getName());
			 messageJson.setNameSender(m.getSender().getName());
			 messageJson.setSent_date(m.getSent_date());
			 messageJson.setPhone(m.getPhone());
			 messageJsons.add(messageJson);
			// m.setIsRead(Boolean.TRUE);
		 }
		 return messageJsons;
	//	return (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m Where (m.reciever = :user or m.sender = :user) and m.isRead = 0").setParameter("user", user).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<MessageJson> findAllMessage() throws IOException {
		List<Message> messages =  (List<Message>) sessionFactory.getCurrentSession().createQuery("select m from Message m ").list();
		List<MessageJson> messageJsons = new ArrayList<MessageJson>();
		for(Message m : messages) {
			 MessageJson messageJson = new MessageJson();
			 messageJson.setContent(Util.decompress(m.getContent()));
			 messageJson.setIsRead(m.getIsRead());
			 messageJson.setName(m.getName());
			 messageJson.setNameReciever(m.getReciever().getName());
			 messageJson.setNameSender(m.getSender().getName());
			 messageJson.setSent_date(m.getSent_date());
			 messageJson.setPhone(m.getPhone());
			 messageJsons.add(messageJson);
			// m.setIsRead(Boolean.TRUE);
		 }
		 return messageJsons;
	}

}
