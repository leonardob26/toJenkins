package com.devone.model;

import java.util.List;

import com.devone.model.struct.ServiceData;
import com.devone.modeljpa.Services;

public interface IServices {
	public List<ServiceData> getListServices(int userId, boolean onlyPaid, String page, int currentOffSet);
	public String save(int id, String description, float price, float quantity, byte status, 
			int typeService, int user, int company, short miles, String dateService);
	public void delete(int id);
	public Services get(int id);
}
