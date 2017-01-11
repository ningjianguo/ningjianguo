package com.comm.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BaseDaoImpl<T> implements IBaseDao<T> {
	protected Class<T> domainClass;
	
	@Resource
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass()
		.getGenericSuperclass();

		this.domainClass = ((Class) type.getActualTypeArguments()[0]);
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void save(T t) {
		getSession().save(t);
	}

	public void delete(T t) {
		getSession().delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getPaging(int pageNo, int pageSize) {
		try {
			Query query = getSession().createQuery(" from  "+ domainClass.getSimpleName());  
			//设置起点  
			query.setFirstResult(pageSize*(pageNo-1));  
			//设置每页显示多少个，设置多大结果。  
			query.setMaxResults(pageSize);  
			return query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
