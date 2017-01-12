package com.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.comm.dao.BaseDaoImpl;
import com.dao.IFile;
import com.entity.File;
import com.entity.User;
import com.entity.Video;
import com.entity.VideoTag;
import com.opensymphony.xwork2.ActionContext;
import com.service.IFileService;
import com.util.FilePathUtil;
import com.util.JsonUtil;

/**
 * @project:ningjianguo
 * @author:Techape
 * @date:2016年12月26日 下午10:58:43
 * @email:1195726908@qq.com
 * @version:v1.0
 * @description:文件业务层实现类
 */
@Service
public class FileServiceImpl extends BaseDaoImpl<File> implements
		IFileService {

	@Resource
	private IFile fileDaoImpl;
	private JSONObject jobj;


	@Override
	public Boolean uploadFile(File f,String fileContentType) {
		int newFileId = getMaxFileId();
		f.setFileDownloadCount(0);
		f.setFileStatu(1);
		f.setFileUploadTime(new Date());
		f.setFileZipName(f.getFileName() + "_blog_" + (newFileId + 1)+"."+fileContentType);
		try {
			save(f);
			if (newFileId == 0) {
				getSession()
						.createSQLQuery(
								"update file set file_zip_name=:filename where file_zip_name=:name")
						.setString("filename",
								f.getFileName() + "_blog_" + getMaxFileId()+"."+fileContentType)
						.setString("name",
								f.getFileName() + "_blog_" + (newFileId + 1)+"."+fileContentType)
						.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}



	@Override
	public String getFileAllInfo() {
		List<File> files = fileDaoImpl.getFileAllInfo();
		Map<String, Object> maps = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject jobj = null;
		try {
			for (File file : files) {
				maps = new HashMap<String, Object>();
				maps.put("fileId", file.getFileId());
				maps.put("fileName", file.getFileName());
				maps.put("fileUploadTime", new SimpleDateFormat("yyyy-MM-dd")
						.format(file.getFileUploadTime()));
				maps.put("fileDownloadCount", file.getFileDownloadCount());
				maps.put("fileStatu", FileStatu(file.getFileStatu()));
				maps.put("fileZipName", file.getFileZipName());
				list.add(maps);
			}
			jobj = new JSONObject();
			jobj.accumulate("total", getFileTotalSize());// total代表一共有多少数据
			jobj.accumulate("rows", list);// row是代表显示的页的数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj.toString();
	}
	@Override
	public int getMaxFileId() {
		try {
			Query query = getSession().createSQLQuery(
					"select ifnull(max(file_id),0) from file");
			BigInteger fileId = (BigInteger) query.list().get(0);
			return fileId.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int getFileTotalSize() {
		Query query = getSession().createSQLQuery("select count(*) from file");
		BigInteger count = (BigInteger) query.list().get(0);
		return count.intValue();
	}

	/**
	 * 判断文件状态
	 */
	public String FileStatu(int num) {
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
	public String getFileStatu() {
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
	public String updateFile(File file) {
		return JsonUtil.toJsonString(fileDaoImpl.updateFile(file));
	}

	@Override
	public String deleteFile(File file) {
		jobj = new JSONObject();
		//删除硬盘中的视频文件
		try {
			Query query = getSession().createSQLQuery("select file_zip_name from file where file_id=:id").setInteger("id", file.getFileId());
			String fileZipName = (String) query.list().get(0);
			java.io.File desFile = new java.io.File(FilePathUtil.getValue("uploadFilePath") + "/"+fileZipName);
			if(desFile.exists()){
				if(desFile.delete()){
					jobj.accumulate("success",fileDaoImpl.deleteFile(file.getFileId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobj.toString();
	}

}
