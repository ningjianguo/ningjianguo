package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.comm.dao.BaseDaoImpl;
import com.dao.IArticle;
import com.entity.Article;
import com.entity.ArticleTag;

/*
 @project:ningjianguo
 @author:Techape
 @date:2016年12月24日 下午11:02:02
 @email:1195726908@qq.com
 @version:v1.0
 @description:博文接口实现类
 */
@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements IArticle {

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTag> getAllArticleTag() {
		Query query = getSession().createQuery("from ArticleTag");
		return query.list();
	}

	@Override
	public int deleteArticle(int articleId) {
		int count = 0;
		try {
			Query query = getSession().createSQLQuery(
					"delete from article where article_id =:id");
			count = query.setParameter("id", articleId).executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	@Override
	public int updateArticle(Article article) {
		int count = 0;
		try {
			Query query = getSession()
					.createSQLQuery(
							"update article set article_title=:title , article_type=:type , article_statu=:statu , article_tag_id=:tagid where article_id=:id");
			count = query.setString("title", article.getArticleTitle())
					.setInteger("type", article.getArticleType())
					.setInteger("statu", article.getArticleStatu())
					.setInteger("tagid", queryTagId(article.getArticleTag().getArticleTagName()))
					.setInteger("id", article.getArticleId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 根据标签名取得标签id
	 * @param tagName 标签名
	 * @return 标签ID
	 */
	@SuppressWarnings("unchecked")
	public int queryTagId(String tagName){
		Query query = getSession().createSQLQuery("select article_tag_id from article_tag where article_tag_name=?").setString(0, tagName);
		List<Integer> tagIds = query.list();
		return tagIds.get(0);
	}
}
