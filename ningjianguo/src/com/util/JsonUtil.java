package com.util;

import net.sf.json.JSONObject;

import com.google.gson.Gson;

/*
	@project:ningjianguo
	@author:Techape
	@date:2016年12月24日 下午11:18:32
	@email:1195726908@qq.com
	@version:v1.0
	@description:json格式转换类
 */
public class JsonUtil {
	
	private static Gson gson = new Gson();
	/**
	 * 转换成json字符串格式
	 * @param object 需转换的对象
	 * @return json格式的字符串
	 */
	public static String toJsonString(Object object){
		return gson.toJson(object);
	}
	
}


