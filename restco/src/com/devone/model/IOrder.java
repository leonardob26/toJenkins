package com.devone.model;

import java.util.List;

import com.devone.model.struct.OrderData;
import com.devone.modeljpa.Orders;


public interface IOrder {
	public List<OrderData> getOrderList(int UserId, boolean onlyPaid, String page, int currentOffSet);
	public String saveOrder(int id,  String dateOrder,  String dateDelivery,  String dateGetPaid,  float price, 
			 short quantity,  int company,  byte status, int user,  int product,  float width, float height,  float deep);
	public void delOrder(int id);
	public Orders getOrder(int id);
	/*@SuppressWarnings("rawtypes")
	public List getCmb(String entity, String condition);*/
}
