package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IVideo;
import com.entity.Video;
import com.entity.VideoTag;

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
	public List<VideoTag> getAllVideoTag() {
		try {
			Query query = getSession().createQuery("from VideoTag");
			return query.list();
		} catch (Exception e) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> getVideAllInfo() {
		Query query = getSession().createQuery("from Video");
		return query.list();
	}
	
	@Override
	public int updateVideo(Video video) {
		int count = 0;
		try {
			Query query = getSession()
					.createSQLQuery(
							"update video set video_name=:name , video_statu=:statu , video_tag_id=:tagid where video_id=:id");
			count = query.setString("name", video.getVideoName())
					.setInteger("statu", video.getVideoStatu())
					.setInteger("tagid", queryTagId(video.getVideoTag().getVideoTagName()))
					.setInteger("id", video.getVideoId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 根据标签名取得标签id
	 * @param tagName 标签名
	 * @return 标签ID
	 */
	@SuppressWarnings("unchecked")
	public int queryTagId(String tagName){
		Query query = getSession().createSQLQuery("select video_tag_id from video_tag where video_tag_name=?").setString(0, tagName);
		List<Integer> tagIds = query.list();
		return tagIds.get(0);
	}
	
	@Override
	public int deleteVideo(int videoId) {
		int count = 0;
		try {
			Query query = getSession().createSQLQuery(
					"delete from video where video_id =:id").setInteger("id", videoId);
			count = query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

}
