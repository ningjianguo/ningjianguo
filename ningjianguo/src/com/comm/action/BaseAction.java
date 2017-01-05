package com.comm.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.IArticleService;
import com.service.ICategoryService;
import com.service.IVideoService;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	private static final long serialVersionUID = -5524321384480423926L;
	@Resource
	protected ICategoryService categoryServiceImpl;
	@Resource
	protected IArticleService articleServiceImpl;
	@Resource
	protected IVideoService videoServiceImpl;
	
	
	protected T model;
	protected HttpServletRequest request;
	protected Map session;
	protected Map application;
	protected HttpServletResponse response;
	public BaseAction() {
		this.session = ActionContext.getContext().getSession();
		this.request = ServletActionContext.getRequest();
		this.application = ActionContext.getContext().getApplication();
		response = ServletActionContext.getResponse();
		try {
			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			Class classz = (Class) type.getActualTypeArguments()[0];

			this.model = (T) classz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 打印json格式输出流到前端
	 */
	public void printJsonStringToBrowser(String jsonString){
		try {
			response.setContentType("html/text;charset=UTF-8");
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public T getModel() {
		return this.model;
	}
}
