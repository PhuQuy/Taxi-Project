package com.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import flexjson.JSONSerializer;

@Entity
@Table(name = "message", catalog = "track_app")
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private User sender;
	private User reciever;
	private String phone;
	private String name;
	private byte[] content;
	private String sent_date;	
	private Boolean isRead;
	
	public Message() {
		super();
	}
	public Message(Integer id, User send, User recieve, User manager,
			byte[] content, String sent_date) {
		super();
		this.id = id;
		this.sender = send;
		this.reciever = recieve;
		this.content = content;
		this.sent_date = sent_date;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender", nullable = false)
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reciever", nullable = false)
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	
	@Column(name = "content", nullable = false, length = 1000)
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Column(name = "sent_date", nullable = false)
	public String getSent_date() {
		return sent_date;
	}
	public void setSent_date(String sent_date) {
		this.sent_date = sent_date;
	}
	
	@Column(name = "phone", nullable = true, length = 100)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "name", nullable = true, length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "is_read", nullable = true)
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	 public static String toConvertJsonArray(Collection<Message> collection) {
	        return new JSONSerializer().include(".sender.name",".reciever.name",".content",".sent_date").exclude(".*").serialize(collection);
	              /*  .exclude(".sender.manage",".sender.roleses",".reciever.manage",".reciever.roleses").serialize(collection);*/
	    }
}
