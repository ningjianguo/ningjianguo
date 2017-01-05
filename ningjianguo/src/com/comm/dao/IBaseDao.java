package com.comm.dao;

import java.util.List;

public abstract interface IBaseDao<T>
{
  public abstract void update(T paramT);

  public abstract void save(T paramT);

  public abstract void delete(T paramT);

  /**
	 * 分页功能的实现
	 * @param pageNo	第几页
	 * @param pageSize	每页显示数量
	 * @return
	 */
	public List<T> getPaging(int pageNo,int pageSize);
}

