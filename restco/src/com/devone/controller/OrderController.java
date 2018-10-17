package com.devone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devone.model.OrderImpl;
import com.devone.model.UtilsImpl;
import com.devone.model.struct.DataWithOffSet;
import com.devone.model.struct.OrderDataForm;
import com.devone.modeljpa.Orders;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderImpl orderDAO;
	@Autowired
	private UtilsImpl utils;
	
	//private UtilsImpl util;
	
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET) 
	public String hello(@PathVariable String name) { 
		String result="Hello "+name; return result; 
	}
	@RequestMapping(value = "/list/{onlyPaid}/{page}/{offSet}", method = RequestMethod.GET)
	public DataWithOffSet getOrderList(@PathVariable boolean onlyPaid, @PathVariable String page,
			@PathVariable int offSet, HttpServletRequest request){
		HttpSession se = request.getSession();
		//int userId = (Integer) se.getAttribute("userId");
		int userId = 2;
		DataWithOffSet ds = new DataWithOffSet();
		ds.setList(orderDAO.getOrderList(userId, onlyPaid, page, offSet));
		ds.setOffset(orderDAO.getCurrentOffSet());
		return ds;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrderDataForm showForm(HttpServletRequest request, @PathVariable int id) { //@PathVariable int id,
		OrderDataForm dataForm = new OrderDataForm();
		Orders ord = orderDAO.getOrder(id);		
		if (ord!=null){
			dataForm.setId(ord.getId());
        	dataForm.setCompany(ord.getCompany().getId());
        	dataForm.setDateDelivery(MyUtils.df.format(ord.getDateDelivery()));
        	dataForm.setDateGetPaid(MyUtils.df.format(ord.getDateGetPaid()));
        	dataForm.setDateOrder(MyUtils.df.format(ord.getDateOrder()));
        	dataForm.setDeep(ord.getDeep());
        	dataForm.setHeight(ord.getHeight());
        	dataForm.setId(id);
        	dataForm.setPrice(ord.getPrice());
        	dataForm.setProducts(ord.getProduct().getId());
        	dataForm.setQuantity(ord.getQuantity());
        	dataForm.setStatus(ord.getStatus().getId());
        	dataForm.setUser(ord.getSecUser().getId());
        	dataForm.setWidth(ord.getWidth());
		} else 
			dataForm.setId(0);
    	dataForm.setCompanyList(utils.getCmb("company",""));
    	dataForm.setProductList(utils.getCmb("products",""));
    	dataForm.setStatusList(utils.getCmb("status", "isOrder=1"));
        	
        return dataForm;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteOrder(HttpServletRequest request, @PathVariable int id) {
		orderDAO.delOrder(id);
		return "SUCCESS";
	}
	/*                        :id/:dateOrder/:dateDelivery/:dateGetPaid/:price/:quantity/:company/:status/:user/:product/:width/:height/:deep*/
	@RequestMapping(value = "/{id}/{dateOrder}/{dateDelivery}/{dateGetPaid}/{price}/{quantity}/{company}/{status}/{user}/{product}/{width}/{height}/{deep}", method = RequestMethod.POST)
	public String updateOrder(HttpServletRequest request, @PathVariable int id, @PathVariable String dateOrder,
			@PathVariable String dateDelivery, @PathVariable String dateGetPaid, @PathVariable float price, 
			@PathVariable short quantity, @PathVariable int company, @PathVariable byte status, 
			@PathVariable int user, @PathVariable int product, @PathVariable float width, 
			@PathVariable float height, @PathVariable float deep) {
		return orderDAO.saveOrder(id, dateOrder, dateDelivery, dateGetPaid, price, quantity, company, status, user, product, width, height, deep);
	}	
}
