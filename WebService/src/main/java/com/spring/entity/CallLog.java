package com.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "call_log", catalog = "track_app")
public class CallLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String phoneCall;
	private String dateCall;
	private String name;
	private Integer callType;
	private Integer duration;
	private Boolean isRead;
	private User owner;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "phone_call", nullable = false, length = 100)
	public String getPhoneCall() {
		return phoneCall;
	}
	public void setPhoneCall(String phoneCall) {
		this.phoneCall = phoneCall;
	}
	
	@Column(name = "date_call", nullable = false, length = 100)
	public String getDateCall() {
		return dateCall;
	}
	public void setDateCall(String dateCall) {
		this.dateCall = dateCall;
	}
	
	@Column(name = "call_type", nullable = false)
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	
	@Column(name = "duration", nullable = false)
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner", nullable = false)
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
}
