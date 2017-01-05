package com.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IVideo;
import com.entity.Video;

/*
 @project:ningjianguo
 @author:Techape
 @date:2016年12月26日 下午10:44:01
 @email:1195726908@qq.com
 @version:v1.0
 @description:视频接口实现类
 */
@Repository
public class VideoDaoImpl extends BaseDaoImpl<Video> implements IVideo {

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> getAllVideoTag() {
		try {
			Query query = getSession().createQuery("from VideoTag");
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> getCategories(int p_categoryId) {
		try {
			Query query = getSession().createSQLQuery("select video_id,video_name from video where video_tag_id=?");
			query.setInteger(0, p_categoryId);
			return query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
