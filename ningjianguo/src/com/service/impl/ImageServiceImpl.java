package com.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.comm.dao.BaseDaoImpl;
import com.dao.IImage;
import com.entity.Image;
import com.entity.ImageFolder;
import com.entity.ImagePage;
import com.service.IImageService;

/*
 @project:ningjianguo
 @author:Techape
 @date:2017年1月16日 下午3:36:31
 @email:1195726908@qq.comzo
 @version:v1.0
 @description:
 */
@Service
public class ImageServiceImpl extends BaseDaoImpl<Image> implements
		IImageService {

	@Resource
	private IImage imageDaoImpl;

	@Override
	public List<ImageFolder> getAllImageFolderName() {
		return imageDaoImpl.getAllImageFolder();
	}

	@Override
	public String getImageStatu() {
		return null;
	}

	@Override
	public Boolean uploadImage(Image v,String imageContentType) {
		int newImageId = getMaxImageId();
		v.setImageUploadTime(new Date());
		v.setImageFileName(v.getImageName() + FILE_SPLIT_SIGN + (newImageId + 1)+imageContentType);
		try {
			save(v);
			if (newImageId == 0) {
				getSession()
						.createSQLQuery(
								"update image set image_file_name=:filename where image_file_name=:name")
						.setString("filename",
								v.getImageName() + FILE_SPLIT_SIGN + getMaxImageId())
						.setString("name",
								v.getImageName() + FILE_SPLIT_SIGN + (newImageId + 1))
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
		return null;
	}

	@Override
	public int getMaxImageId() {
		try {
			Query query = getSession().createSQLQuery(
					"select ifnull(max(image_id),0) from image");
			BigInteger videoId = (BigInteger) query.list().get(0);
			return videoId.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getImageName(int ImageId) {
		return null;
	}

	@Override
	public String getImageAllInfo() {
		return null;
	}

	@Override
	public int getImageTotalSize() {
		return 0;
	}

	@Override
	public String updateImage(Image Image) {
		return null;
	}

	@Override
	public String deleteImage(Image Image) {
		return null;
	}

	@Override
	public List<Image> getImageInfo(ImagePage iPage) {
		return getPaging(iPage.getPageNo(), IMAGE_PAGE_SIZE,"where imageFolder.imageFolderId="+iPage.getFolderId());//设置每页显示照片数量为30张
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImagePage imageCount(int imageFolderId) {
		List<Image> images = getSession().createQuery("from Image where imageFolder.imageFolderId="+imageFolderId).list();
		ImagePage iPage = new ImagePage();
		int totalRecords = images.size();
		int totalPages = totalRecords % IMAGE_PAGE_SIZE == 0 ? (totalRecords/IMAGE_PAGE_SIZE) : (totalRecords/IMAGE_PAGE_SIZE)+1;
		iPage.setPageRecords(IMAGE_PAGE_SIZE);
		iPage.setTotalRecords(totalRecords);
		iPage.setTotalPage(totalPages);
		return iPage;
	}

}
