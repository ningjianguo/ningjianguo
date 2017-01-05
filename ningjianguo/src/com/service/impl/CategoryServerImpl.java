package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.ICategory;
import com.entity.Category;
import com.service.ICategoryService;
import com.util.JsonUtil;

@Service
public class CategoryServerImpl implements ICategoryService {

	@Resource
	private ICategory categoryDaoImpl;

	public String getCategories() {
		List<Category> categories = categoryDaoImpl.getCategories();
		return JsonUtil.toJsonString(categories);
	}
}
