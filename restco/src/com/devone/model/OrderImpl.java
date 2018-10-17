package com.devone.model;

import java.text.ParseException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.devone.controller.MyUtils;
import com.devone.model.struct.OrderData;
import com.devone.modeljpa.Company;
import com.devone.modeljpa.Orders;
import com.devone.modeljpa.Product;
import com.devone.modeljpa.SecUser;
import com.devone.modeljpa.Status;

public class OrderImpl implements IOrder {

	private SessionFactory sessionFactory;
	private Session sess;
	private int currentOffSet;
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }	
    
	@Override
	public List<OrderData> getOrderList(int userId, boolean onlyPaid, String page, int currentOffSet) {
		final byte elementPerPage=10;
		List<OrderData> ls;
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		
		try {
			int companyId = sess.find(SecUser.class, userId).getCompany().getId();
			String whereSql = (onlyPaid ? "o.status_id=5" : "o.status_id<>5")
					+ (companyId == -1 ? "" : " and company_id = " + companyId);
			int maxOffSet = sess.createNativeQuery("SELECT count(*) FROM orders o where " + whereSql).getFirstResult()
					/ elementPerPage;
			//int maxOffSet = db.getDataValueInt("SELECT count(*) FROM orders o where " + whereSql)/elementPerPage;
			this.currentOffSet = handOffSet(page, currentOffSet, elementPerPage, maxOffSet);
			String sql = "SELECT o.id, c.name nameCompany, p.name nameProduct, date_order, width, height, deep, "
					+ "price, quantity, date_delivery, s.name statusName "
					+ "FROM orders o join products p on o.products_id = p.id join company c on o.company_id=c.id "
					+ "join status s on o.status_id=s.id where " + whereSql + " limit "
					+ this.currentOffSet * elementPerPage + "," + elementPerPage;
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
	public String saveOrder(int id,  String dateOrder,  String dateDelivery,  String dateGetPaid,  float price, 
			 short quantity,  int company,  byte status, int user,  int product,  float width, float height,  float deep) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		
		Transaction tx = sess.beginTransaction();
		Orders or = sess.find(Orders.class, id);
		try {
			if (or==null)
				or = new Orders();
				
			
			try {
				if (dateOrder!=null  && !"null".equals(dateOrder)) //new java.sql.Timestamp(dateOrder.getTime())
					or.setDateOrder(new java.sql.Timestamp(MyUtils.dfPostman.parse(dateOrder).getTime()));
				
				if (dateDelivery!=null && !"null".equals(dateDelivery))			//serv.setDateService(new java.sql.Timestamp(dateService.getTime()));
					or.setDateDelivery(new java.sql.Timestamp(MyUtils.dfPostman.parse(dateDelivery).getTime()));
				if (dateGetPaid!=null && !"null".equals(dateGetPaid))
						or.setDateGetPaid(new java.sql.Timestamp(MyUtils.dfPostman.parse(dateGetPaid).getTime()));
			} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
			or.setPrice(price);
			or.setQuantity(quantity);
			
			Company compy = new Company();
			compy.setId(company);
			or.setCompany(compy);
			
			Status st = new Status();
			st.setId((byte) status);
			or.setStatus(st);
			
			SecUser su = new SecUser();
			su.setId(user);		
			or.setSecUser(su);
			
			Product prod = new Product();
			prod.setId(product);
			or.setProduct(prod);
			
			or.setWidth(width);
			or.setHeight(height);
			or.setDeep(deep);
			
			sess.save(or);
			tx.commit();
			return "{ \"id\" : " + String.valueOf(or.getId()) + " }";
		} catch (Exception e){
			tx.rollback();
			return "FAIL";
		}
		finally {
			sess.close();
		}
	}

	@Override
	public void delOrder(int id) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		Transaction tx = sess.beginTransaction();
		try {
			int result = sess.createQuery("delete Orders where id = :id").setParameter("id", id).executeUpdate();
			tx.commit();
		} catch (Exception e){
			tx.rollback();
		}
		finally {
			sess.close();
		}
	}

	@Override
	public Orders getOrder(int id) {
		try {
			sess = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= sessionFactory.openSession();
		}
		try {
			return sess.find(Orders.class, id);
		} catch (Exception e) {
			return null;
		} finally{
			sess.close();
		}

	}

    private int handOffSet(String page, int currentOffSet, final byte elementPerPage, int maxOffSet) {
		switch(page){
		case "previous":
			if (currentOffSet!=0)
				currentOffSet--;
			break;
		case "next":
			if (currentOffSet<maxOffSet)
				currentOffSet++;
			break;
		default:
			currentOffSet = Integer.parseInt(page)-1;
			if (currentOffSet>maxOffSet)
				currentOffSet=maxOffSet;
			break;
		}
		return currentOffSet;
	}
	public int getCurrentOffSet() {
		return currentOffSet;
	}

	public void setCurrentOffSet(int currentOffSet) {
		this.currentOffSet = currentOffSet;
	}
}
