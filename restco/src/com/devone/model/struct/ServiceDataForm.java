package com.devone.model.struct;

import java.util.List;

public class ServiceDataForm {

	private int id;
	private String description;
	private float price;
	private float quantity;
	private String date;
	private int status = 1;
	private int typeService;
	private int user;
	private int company = 0;
	private short miles;
	private String dateService;
	private List typeServicetList;
	private List companyList;
	private List statusList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTypeService() {
		return typeService;
	}
	public void setTypeService(int typeService) {
		this.typeService = typeService;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public short getMiles() {
		return miles;
	}
	public void setMiles(short miles) {
		this.miles = miles;
	}
	public String getDateService() {
		return dateService;
	}
	public void setDateService(String dateService) {
		this.dateService = dateService;
	}
	public List getTypeServicetList() {
		return typeServicetList;
	}
	public void setTypeServicetList(List typeServicetList) {
		this.typeServicetList = typeServicetList;
	}
	public List getCompanyList() {
		return companyList;
	}
	public void setCompanyList(List companyList) {
		this.companyList = companyList;
	}
	public List getStatusList() {
		return statusList;
	}
	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}
	
}
