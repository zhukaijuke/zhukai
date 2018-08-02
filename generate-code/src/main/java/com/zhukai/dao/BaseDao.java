package com.zhukai.dao;

import java.util.List;
import java.util.Map;

/**
 * @author zhukai
 */
public interface BaseDao<T> {

	void save(T t);

	void saveBatch(List<T> list);

	int update(T t);

	int updateByVersion(T t);

	int delete(Long id);

	int deleteBatch(List<Long> ids);

	T findById(Long id);

	List<T> findListById(List<Long> ids);

	List<T> findPageInfo(Map<String, Object> map);
	
	long countTotal(Map<String, Object> map);

}
