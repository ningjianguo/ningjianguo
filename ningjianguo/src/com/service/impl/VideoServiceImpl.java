package com.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.comm.dao.BaseDaoImpl;
import com.dao.IVideo;
import com.entity.User;
import com.entity.Video;
import com.opensymphony.xwork2.ActionContext;
import com.service.IVideoService;
import com.util.JsonUtil;

/**
 * @project:ningjianguo
 * @author:Techape
 * @date:2016年12月26日 下午10:58:43
 * @email:1195726908@qq.com
 * @version:v1.0
 * @description:视频业务层实现类
 */
@Service
public class VideoServiceImpl extends BaseDaoImpl<Video> implements
		IVideoService {

	@Resource
	private IVideo videoDaoImpl;

	@Override
	public List<Video> getAllVideoTag() {
		return videoDaoImpl.getAllVideoTag();
	}

	@Override
	public Boolean uploadVideo(Video v) {
		Map session = ActionContext.getContext().getSession();
		User admin = (User) session.get("admin");
		v.setVideoDownloadCount(0);
		v.setVideoUploadEditer(admin.getUserName());
		v.setVideoUploadTime(new Date());
		try {
			save(v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String getCategories(int p_categoryId) {
		List<Video> categories = videoDaoImpl.getCategories(p_categoryId);
		return JsonUtil.toJsonString(categories);
	}

	@Override
	public int getNewVideoId() {
		try {
			Query query = getSession().createSQLQuery(
					"select max(video_id) from video");
			int videoId = (Integer) query.list().get(0);
			return videoId;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getVideo(int videoId) {
		try {
			Query query = getSession().createSQLQuery(
					"select video_id,video_name from video where video_id=?");
			query.setInteger(0, videoId);
			List<Video> video = query.list();
			return JsonUtil.toJsonString(video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
