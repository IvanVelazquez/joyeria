package com.proyect.joyeria.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="prueba")
public class prueba implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4894297926780164624L;

	@Id
	@Column(name="id_user")
	private Integer userId;
 
	@Column(name = "user")
	private String userName;
 
	@Column(name = "pass")
	private String password;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
