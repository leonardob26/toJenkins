package com.devone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devone.model.ServicesImpl;
import com.devone.model.UtilsImpl;
import com.devone.model.struct.DataWithOffSet;
import com.devone.model.struct.ServiceDataForm;
import com.devone.modeljpa.Services;


@RestController
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	private ServicesImpl servicesDAO;
	@Autowired
	private UtilsImpl utilsDAO;
	
	//private UtilsImpl util;
	
	@RequestMapping(value = "/list/{onlyPaid}/{page}/{offSet}", method = RequestMethod.GET)
	public DataWithOffSet getServiceList(@PathVariable boolean onlyPaid, @PathVariable String page,
			@PathVariable int offSet, HttpServletRequest request){
		HttpSession se = request.getSession();
		//int userId = (Integer) se.getAttribute("userId");
		int userId = 2;
		DataWithOffSet ds = new DataWithOffSet();
		ds.setList(servicesDAO.getListServices(userId, onlyPaid, page, offSet));
		ds.setOffset(servicesDAO.getCurrentOffSet());
		return ds;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ServiceDataForm getService(HttpServletRequest request, @PathVariable int id) { //@PathVariable int id,
		ServiceDataForm dataForm = new ServiceDataForm();
		Services serv = servicesDAO.get(id);
			
		if (serv!=null){
			dataForm.setId(serv.getId());
        	dataForm.setCompany(serv.getCompanyId());
        	dataForm.setDateService(MyUtils.df.format(serv.getDateService()));
        	dataForm.setDescription(serv.getDescription());
        	dataForm.setMiles(serv.getMiles());
        	dataForm.setPrice(serv.getPrice());
        	dataForm.setQuantity(serv.getQuantity());
        	dataForm.setStatus(serv.getStatus());
        	dataForm.setTypeService(serv.getTypeService());
        	dataForm.setUser(serv.getUserId());
		} else 
			dataForm.setId(0);
		
    	dataForm.setCompanyList(utilsDAO.getCmb("company",""));
    	dataForm.setTypeServicetList(utilsDAO.getCmb("type_service",""));
    	dataForm.setStatusList(utilsDAO.getCmb("status", "isService=1"));
        	
        return dataForm;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(HttpServletRequest request, @PathVariable int id) {
		servicesDAO.delete(id);
		return "SUCCESS";
	}
	/*                        :id/:description/:price/:quantity/byte status/:typeService/:user/:company/short miles/:dateService*/ 
	// /{id}/{description}/{price}/{quantity}/{status}/{typeService}/{user}/{company}/{miles}/{dateService}
	@RequestMapping(value = "/{id}/{description}/{price}/{quantity}/{status}/{typeService}/{user}/{company}/{miles}/{dateService}", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @PathVariable int id, @PathVariable String description,
			@PathVariable float price, @PathVariable short quantity, @PathVariable byte status, @PathVariable int typeService, 
			@PathVariable int user, @PathVariable int company, @PathVariable short miles, @PathVariable String dateService) {
		//:id/:description/:price/:quantity/byte status/:typeService/:user/:company/short miles/:dateService
		return servicesDAO.save(id, description, price, quantity, status, typeService, user, company, miles, dateService);
	}	
}
