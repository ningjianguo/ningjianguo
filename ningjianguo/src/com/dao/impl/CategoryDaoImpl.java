 package com.dao.impl;
 
 import java.util.List;

import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.ICategory;
import com.entity.Category;
 
 @Repository
 public class CategoryDaoImpl extends BaseDaoImpl<Category>
   implements ICategory
 {
   @SuppressWarnings("unchecked")
public List<Category> getCategories()
   {
     return getSession().createQuery("from Category").list();
   }
 }
