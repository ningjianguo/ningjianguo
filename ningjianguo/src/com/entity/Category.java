 package com.entity;
 
 import java.io.Serializable;
 
 public class Category
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer categoryId;
   private String categoryName;
   private Integer categoryParentid;
   private String categoryIcon;
   private String categoryUrl;
 
   public Category()
   {
   }
 
   public Category(String categoryName, Integer categoryParentid, String categoryIcon, String categoryTarget)
   {
/* 27 */     this.categoryName = categoryName;
/* 28 */     this.categoryParentid = categoryParentid;
/* 29 */     this.categoryIcon = categoryIcon;
   }
 
   public Category(String categoryName, Integer categoryParentid, String categoryIcon, String categoryUrl, String categoryTarget)
   {
/* 36 */     this.categoryName = categoryName;
/* 37 */     this.categoryParentid = categoryParentid;
/* 38 */     this.categoryIcon = categoryIcon;
/* 39 */     this.categoryUrl = categoryUrl;
   }
 
   public Integer getCategoryId()
   {
/* 46 */     return this.categoryId;
   }
 
   public void setCategoryId(Integer categoryId) {
/* 50 */     this.categoryId = categoryId;
   }
 
   public String getCategoryName() {
/* 54 */     return this.categoryName;
   }
 
   public void setCategoryName(String categoryName) {
/* 58 */     this.categoryName = categoryName;
   }
 
   public Integer getCategoryParentid() {
/* 62 */     return this.categoryParentid;
   }
 
   public void setCategoryParentid(Integer categoryParentid) {
/* 66 */     this.categoryParentid = categoryParentid;
   }
 
   public String getCategoryIcon() {
/* 70 */     return this.categoryIcon;
   }
 
   public void setCategoryIcon(String categoryIcon) {
/* 74 */     this.categoryIcon = categoryIcon;
   }
 
   public String getCategoryUrl() {
/* 78 */     return this.categoryUrl;
   }
 
   public void setCategoryUrl(String categoryUrl) {
/* 82 */     this.categoryUrl = categoryUrl;
   }
 }

