package com.devone.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilsImpl implements IUtils {
	private SessionFactory mySessionFactory;
	private Session sess;
	@Autowired
    public void setSessionFactory(SessionFactory mySessionFactory) {
        this.mySessionFactory = mySessionFactory;
    }	
	@Override
	public List getCmb(String table, String condition) {
		try {
			sess = mySessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			sess= mySessionFactory.openSession();
		}
		try {
			String sql = "select id, name from " + table + (condition.isEmpty()?"":" where " + condition);
			return sess.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			return null;
		} finally{
			sess.close();
		}
	}
	
	public static int handOffSet(String page, int currentOffSet, final byte elementPerPage, int maxOffSet) {
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

}
