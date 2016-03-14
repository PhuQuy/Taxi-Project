package testXr;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import flexjson.JSONSerializer;

public class Message implements Serializable{

	private Integer id;
	private String nameSender;
	private String nameReciever;
	private String content;
	private Date sent_date;	
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
	public Date getSent_date() {
		return sent_date;
	}
	public void setSent_date(Date sent_date) {
		this.sent_date = sent_date;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}
