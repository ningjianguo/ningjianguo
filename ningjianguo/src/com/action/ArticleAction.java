package com.action;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.comm.action.BaseAction;
import com.entity.Article;

/*
	@project:ningjianguo
	@author:Techape
	@date:2016年12月22日 下午11:31:09
	@email:1195726908@qq.com
	@version:v1.0
	@description:博客文章控制器
 */
@Controller
@Scope("prototype")
public class ArticleAction extends BaseAction<Article>{
	
	private static final long serialVersionUID = 1L;
	String page;
	String rows;
	String articleString;
	/**
	 * 跳转到博文编辑页面
	 */
	public String editerArticle(){
		request.setAttribute("tags", articleServiceImpl.getAllArticleTag());
		return "editer";
	}
	/**
	 * 保存博文
	 */
	public String saveArticle(){
		if(articleServiceImpl.saveArticle(getModel())){
			request.setAttribute("saveinfo", "发表成功！");
		}else{
			request.setAttribute("saveinfo", "发表失败！");
		}
		return "save";
	}
	
	/**
	 * 跳转到管理文章页面
	 */
	public String forwardManageArticle(){
		return "manageArticle";
	}
	
	/**
	 * 加载文章信息
	 */
	public String loadArticle(){
		articleString = articleServiceImpl.getAllArticle(Integer.parseInt(page), Integer.parseInt(rows));
		printJsonStringToBrowser(articleString);
		return null;
	}
	/**
	 * 加载标签信息 
	 */
	public String loadTagArticle(){
		articleString = articleServiceImpl.getAllTag();
		printJsonStringToBrowser(articleString);
		return null;
	}
	/**
	 * 加载类型信息 
	 */
	public String loadTypeArticle(){
		articleString = articleServiceImpl.getAllTagType();
		printJsonStringToBrowser(articleString);
		return null;
	}
	/**
	 * 加载状态信息 
	 */
	public String loadStatuArticle(){
		articleString = articleServiceImpl.getAllTagStatu();
		printJsonStringToBrowser(articleString);
		return null;
	}
	
	/**
	 * 更新文章
	 */
	public String updateArticle(){
		articleString = articleServiceImpl.updateArticle(getModel());
		try {
			response.getWriter().write(articleString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文章
	 */
	public String deleteArticle(){
		articleString = articleServiceImpl.deleteArticle(getModel());
		try {
			response.getWriter().write(articleString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPage(String page) {
		this.page = page;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

}


