package com.spring.entity;
// Generated May 18, 2015 3:59:25 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "track_app")
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email", unique = true, nullable = false, length = 200)
	private String email;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	
	@Column(name = "enable", nullable = false)
	private boolean enable;
	
	@OneToMany
	@JoinColumn(name = "manage", nullable = true)
	private Set<User> manage = new HashSet<User>();	
	
	@ManyToMany
	@JoinTable(name = "user_roles", catalog = "track_app", joinColumns = { @JoinColumn(name = "user_email", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "roleid", nullable = false, updatable = false) })
	private Set<Roles> roleses = new HashSet<Roles>();

	public User() {
	}

	public void setManage(Set<User> manage) {
		this.manage = manage;
	}
	
	public User(String email, String name, String password, boolean enable,Set<Roles> roleses) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.enable = enable;
		this.roleses = roleses;
	}

	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public boolean isEnable() {
		return this.enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public User(String email, String name, String password, boolean enable) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.enable = enable;
	}

	
	public Set<User> getManage() {
		return manage;
	}

	public Set<Roles> getRoleses() {
		return this.roleses;
	}

	public void setRoleses(Set<Roles> roleses) {
		this.roleses = roleses;
	}

}
