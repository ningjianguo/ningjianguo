package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IImage;
import com.entity.Image;
import com.entity.ImageFolder;

/*
	@project:ningjianguo
	@author:Techape
	@date:2017年1月16日 下午3:26:08
	@email:1195726908@qq.com
	@version:v1.0
	@description:相片接口实现类
 */
@Repository
public class ImageDaoImpl extends BaseDaoImpl<Image> implements IImage {

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageFolder> getAllImageFolder() {
		Query query = getSession().createQuery("from ImageFolder");
		return query.list();
	}

	@Override
	public List<Image> getImageAllInfo() {
		return null;
	}

	@Override
	public int updateImage(Image image) {
		return 0;
	}

	@Override
	public int deleteImage(int imageId) {
		return 0;
	}

}


