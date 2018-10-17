package com.devone.model;

import java.text.ParseException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.devone.controller.MyUtils;
import com.devone.model.struct.ServiceData;
import com.devone.modeljpa.Company;
import com.devone.modeljpa.Orders;
import com.devone.modeljpa.Product;
import com.devone.modeljpa.SecUser;
import com.devone.modeljpa.Services;
import com.devone.modeljpa.Status;

public class ServicesImpl implements IServices {

	private SessionFactory sessionFactory;
	private Session sess;
	private int currentOffSet;
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }	
	
	@Override
	public List<ServiceData> getListServices(int userId, boolean onlyPaid, String page, int currentOffSet) {
		final byte elementPerPage=10;
		List<ServiceData> ls;
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		
		try {
			int companyId = sess.find(SecUser.class, userId).getCompany().getId();
			String whereSql = (onlyPaid ? "se.status_id=5" : "se.status_id<>5")
					+ (companyId == -1 ? "" : " and company_id = " + companyId);
			int maxOffSet = sess.createNativeQuery("SELECT count(*) FROM service o where " + whereSql).getFirstResult()
					/ elementPerPage;
			
			this.currentOffSet = UtilsImpl.handOffSet(page, currentOffSet, elementPerPage, maxOffSet);
			
			String sql = "SELECT se.id, description, price, DATE_FORMAT(date,'%m/%d/%Y'), DATE_FORMAT(date_service, '%m/%d/%Y'), miles, ts.name typeServiceName, "+
					"st.name statusName, co.name companyName " +
					"FROM service se join status st on status_id=st.id " +
					"join type_service ts on se.type_service_id = ts.id " +
					"join company co on se.company_id=co.id where " + whereSql + 
					" limit " + currentOffSet*elementPerPage + "," + elementPerPage;
			
			/*for (Object obj : sess.createNamedQuery(sql).getResultList()) {
			arrayForAdmin.add((Admin)oneObject);
			}*/
			ls = sess.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			sess.close();
		}
		return ls;
	}

	
	@Override
	public String save(int id, String description, float price, float quantity, byte status, int typeService, int user,
			int company, short miles, String dateService) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		Services serv;
		Transaction tx = sess.beginTransaction();
		try {
			serv = sess.find(Services.class, id);
			if (serv==null)
				serv = new Services();
			
			try {
				if (dateService!=null  && !"null".equals(dateService)) //new java.sql.Timestamp(dateService.getTime())
					serv.setDateService(new java.sql.Timestamp(MyUtils.dfPostman.parse(dateService).getTime()));
			} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
			serv.setPrice(price);
			serv.setQuantity(quantity);
			serv.setCompanyId(company);
			serv.setDescription(description);
			serv.setMiles(miles);
			serv.setPrice(price);
			serv.setQuantity(quantity);
			serv.setStatus(status);
			serv.setTypeService(typeService);
			serv.setUserId(user);
			sess.save(serv);
			tx.commit();
			return "{ \"id\":" + serv.getId() + "}";
		} catch (Exception e){
			tx.rollback();
			return "FAIL";
		}
		finally {
			sess.close();
		}
	}

	@Override
	public void delete(int id) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		Transaction tx = sess.beginTransaction();
		try {
			int result = sess.createQuery("delete Services where id = :id").setParameter("id", id).executeUpdate();
			tx.commit();
		} catch (Exception e){
			tx.rollback();
		}
		finally {
			sess.close();
		}

	}

	@Override
	public Services get(int id) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		try {
			return sess.find(Services.class, id);
		} catch (Exception e) {
			return null;
		} finally{
			sess.close();
		}
	}
	
	public int getCurrentOffSet() {
		return currentOffSet;
	}

	public void setCurrentOffSet(int currentOffSet) {
		this.currentOffSet = currentOffSet;
	}
}
