package com.devone.modeljpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the service database table.
 * 
 */
@Entity
@Table(name="service")
@NamedQuery(name="Services.findAll", query="SELECT s FROM Services s")
public class Services implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="company_id")
	private int companyId;

	private Timestamp date;

	@Column(name="date_service")
	private Timestamp dateService;

	private String description;

	private short miles;

	private float price;

	private float quantity;

	@Column(name="user_id")
	private int userId;

	//uni-directional many-to-one association to Status
	@Column(name="status_id")
	private byte status;

	//uni-directional many-to-one association to TypeService
	@Column(name="type_service_id")
	private int typeService;

	public Services() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Timestamp getDateService() {
		return this.dateService;
	}

	public void setDateService(Timestamp dateService) {
		this.dateService = dateService;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getMiles() {
		return this.miles;
	}

	public void setMiles(short miles) {
		this.miles = miles;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	public int getTypeService() {
		return typeService;
	}

	public void setTypeService(int typeService) {
		this.typeService = typeService;
	}



}