package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IFile;
import com.entity.File;

/*
 @project:ningjianguo
 @author:Techape
 @date:2016年12月26日 下午10:44:01
 @email:1195726908@qq.com
 @version:v1.0
 @description:文件接口实现类
 */
@Repository
public class FileDaoImpl extends BaseDaoImpl<File> implements IFile {


	@SuppressWarnings("unchecked")
	@Override
	public List<File> getFileAllInfo() {
		Query query = getSession().createQuery("from File");
		return query.list();
	}
	
	@Override
	public int updateFile(File file) {
		int count = 0;
		try {
			Query query = getSession()
					.createSQLQuery(
							"update file set file_name=:name ,file_statu=:statu where file_id=:id");
			count = query.setString("name", file.getFileName())
					.setInteger("statu", file.getFileStatu())
					.setInteger("id", file.getFileId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@Override
	public int deleteFile(int fileId) {
		int count = 0;
		try {
			Query query = getSession().createSQLQuery(
					"delete from file where file_id =:id").setInteger("id", fileId);
			count = query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

}
