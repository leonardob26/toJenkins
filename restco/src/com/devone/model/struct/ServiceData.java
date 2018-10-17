package com.devone.model.struct;

public class ServiceData {
	private int id;
	private String description, price, date_service, miles, typeServiceName, statusName, companyName;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDate_service() {
		return date_service;
	}
	public void setDate_service(String date_service) {
		this.date_service = date_service;
	}
	public String getMiles() {
		return miles;
	}
	public void setMiles(String miles) {
		this.miles = miles;
	}
	public String getTypeServiceName() {
		return typeServiceName;
	}
	public void setTypeServiceName(String typeServiceName) {
		this.typeServiceName = typeServiceName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
