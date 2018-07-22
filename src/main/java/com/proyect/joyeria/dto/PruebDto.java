package com.proyect.joyeria.dto;

public class PruebDto {
	
	private Integer userId;
	private String userName;
	private String password;
	
	
	
	
	public PruebDto(Integer userId, String userName, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
	
	
	public PruebDto() {
		super();
		// TODO Auto-generated constructor stub
	}


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
