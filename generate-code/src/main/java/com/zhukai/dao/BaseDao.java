package com.zhukai.dao;

import java.util.List;

/**
 * 通用Dao查询
 */
public interface BaseDao<T> {

    int insert(T t);

	int update(T t);

	int delete(Long id);

	T findById(Long id);

	List<T> findList(T t);

}
