package com.dao;

import java.util.List;

import com.entity.Video;

/*
	@project:ningjianguo
	@author:Techape
	@date:2016年12月26日 下午10:42:11
	@email:1195726908@qq.com
	@version:v1.0
	@description:视频接口
 */
public interface IVideo {
	/**
	 * 获得 所有视频标签
	 */
	public List<Video> getAllVideoTag();
	
	/*
	 * 根据父目录获取视频条目
	 * @param p_categoryId 父目录id
	 * @return 视频条目集合
	 */
	
	public List<Video> getCategories(int p_categoryId);
}


