package com.spring.entity;

import java.io.Serializable;

public class MessageJson implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nameSender;
	private String nameReciever;
	private String content;
	private String name;
	private String sent_date;	
	private String phone;
	private Boolean isRead;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameSender() {
		return nameSender;
	}
	public void setNameSender(String nameSender) {
		this.nameSender = nameSender;
	}
	public String getNameReciever() {
		return nameReciever;
	}
	public void setNameReciever(String nameReciever) {
		this.nameReciever = nameReciever;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSent_date() {
		return sent_date;
	}
	public void setSent_date(String sent_date) {
		this.sent_date = sent_date;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
