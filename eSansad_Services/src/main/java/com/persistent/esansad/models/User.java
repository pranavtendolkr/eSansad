package com.persistent.esansad.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String token;
	private String userName;
	private String password;
	
	

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
