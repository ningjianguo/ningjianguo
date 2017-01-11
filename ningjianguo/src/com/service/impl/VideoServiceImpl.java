package com.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.comm.dao.BaseDaoImpl;
import com.dao.IVideo;
import com.entity.User;
import com.entity.Video;
import com.entity.VideoTag;
import com.opensymphony.xwork2.ActionContext;
import com.service.IVideoService;
import com.util.FilePathUtil;
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
	private JSONObject jobj;

	@Override
	public List<VideoTag> getAllVideoTag() {
		return videoDaoImpl.getAllVideoTag();
	}

	@Override
	public Boolean uploadVideo(Video v) {
		int newVideoId = getNewVideoId();
		Map session = ActionContext.getContext().getSession();
		User admin = (User) session.get("admin");
		v.setVideoDownloadCount(0);
		v.setVideoStatu(1);
		v.setVideoUploadEditer(admin.getUserName());
		v.setVideoUploadTime(new Date());
		v.setVideoFileName(v.getVideoName() + "_blog_" + (newVideoId + 1));
		try {
			save(v);
			if (newVideoId == 0) {
				getSession()
						.createSQLQuery(
								"update video set video_file_name=:filename where video_file_name=:name")
						.setString("filename",
								v.getVideoName() + "_blog_" + getNewVideoId())
						.setString("name",
								v.getVideoName() + "_blog_" + (newVideoId + 1))
						.executeUpdate();
			}
		} catch (Exception e) {
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
					"select ifnull(max(video_id),0) from video");
			BigInteger videoId = (BigInteger) query.list().get(0);
			return videoId.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getVideoName(int videoId) {
		try {
			Query query = getSession()
					.createSQLQuery(
							"select video_id,video_name,video_file_name from video where video_id=?");
			query.setInteger(0, videoId);
			List<Video> video = query.list();
			return JsonUtil.toJsonString(video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getVideoAllInfo() {
		List<Video> videos = videoDaoImpl.getVideAllInfo();
		Map<String, Object> maps = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject jobj = null;
		try {
			for (Video video : videos) {
				maps = new HashMap<String, Object>();
				maps.put("videoId", video.getVideoId());
				maps.put("videoName", video.getVideoName());
				maps.put("videoUploadEditer", video.getVideoUploadEditer());
				maps.put("videoUploadTime", new SimpleDateFormat("yyyy-MM-dd")
						.format(video.getVideoUploadTime()));
				maps.put("videoDownloadCount", video.getVideoDownloadCount());
				maps.put("videoTag.videoTagName", video.getVideoTag()
						.getVideoTagName());
				maps.put("videoStatu", videoStatu(video.getVideoStatu()));
				maps.put("videoFileName", video.getVideoFileName() + ".mp4");
				list.add(maps);
			}
			jobj = new JSONObject();
			jobj.accumulate("total", getVideoTotalSize());// total代表一共有多少数据
			jobj.accumulate("rows", list);// row是代表显示的页的数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj.toString();
	}

	@Override
	public int getVideoTotalSize() {
		Query query = getSession().createSQLQuery("select count(*) from video");
		BigInteger count = (BigInteger) query.list().get(0);
		return count.intValue();
	}

	/**
	 * 判断视频状态
	 */
	public String videoStatu(int num) {
		String temp = null;
		switch (num) {
		case 1:
			temp = "未发布";
			break;

		case 2:
			temp = "已发布";
			break;
		}
		return temp;
	}

	@Override
	public String getVideoStatu() {
		Map<String, Object> statu1 = new HashMap<String, Object>();
		Map<String, Object> statu2 = new HashMap<String, Object>();
		List<Map<String, Object>> statuList = new ArrayList<Map<String, Object>>();
		statu1.put("statuName", "已发布");
		statu1.put("statuId", 1);
		statu2.put("statuName", "未发布");
		statu2.put("statuId", 2);
		statuList.add(statu1);
		statuList.add(statu2);
		return JsonUtil.toJsonString(statuList);
	}

	@Override
	public String getAllTagType() {
		List<VideoTag> tags = videoDaoImpl.getAllVideoTag();
		Map<String, Object> maps = null;
		List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
		for (VideoTag videoTag : tags) {
			maps = new HashMap<String, Object>();
			maps.put("tagId", videoTag.getVideoTagId());
			maps.put("tagName", videoTag.getVideoTagName());
			tagList.add(maps);
		}
		return JsonUtil.toJsonString(tagList);
	}

	@Override
	public String updateVideo(Video video) {
		return JsonUtil.toJsonString(videoDaoImpl.updateVideo(video));
	}

	@Override
	public String deleteVideo(Video video) {
		jobj = new JSONObject();
		//删除硬盘中的视频文件
		try {
			Query query = getSession().createSQLQuery("select video_file_name from video where video_id=:id").setInteger("id", video.getVideoId());
			String videoFileName = (String) query.list().get(0);
			File desFile = new File(FilePathUtil.getValue("uploadFilePath") + "/"+videoFileName+".mp4");
			if(desFile.exists()){
				if(desFile.delete()){
					jobj.accumulate("success",videoDaoImpl.deleteVideo(video.getVideoId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj.toString();
	}
}
