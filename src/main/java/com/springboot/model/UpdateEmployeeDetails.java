package com.springboot.model;

import javax.persistence.Column;

public class UpdateEmployeeDetails {
	private String name;
	@Column(name="address")
	private String address;
	@Column(name="phonenumber")
	private long phonenumber;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}
	

}
