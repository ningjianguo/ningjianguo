package com.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IUser;
import com.entity.User;

/*
	@project:ningjianguo
	@author:Techape
	@date:2016年12月19日 下午10:46:42
	@email:1195726908@qq.com
	@version:v1.0
	@description:用户表的Dao实现类
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUser {

	@Override
	public Boolean isExistUser(User user) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("select count(*) from user where user_name=? and user_password=?")
					.setString(0, user.getUserName()).setString(1, user.getUserPassword());
		BigInteger count = (BigInteger)query.list().get(0);
		if(count.intValue() == 0){
			return false;
		}else{
			return true;
		}
	}
}


