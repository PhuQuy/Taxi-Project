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
@Table(name = "app_item", catalog = "track_app")
public class AppItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String label;
	private String name;
	private String packageName;
	private Boolean included;
	private Boolean important;
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
	
	@Column(name = "label", nullable = false, length = 1000)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(name = "name", nullable = true, length = 1000)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "packageName", nullable = false, length = 1000)
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	@Column(name = "included", nullable = true)
	public Boolean getIncluded() {
		return included;
	}
	public void setIncluded(Boolean included) {
		this.included = included;
	}
	
	@Column(name = "important", nullable = true)
	public Boolean getImportant() {
		return important;
	}
	public void setImportant(Boolean important) {
		this.important = important;
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
