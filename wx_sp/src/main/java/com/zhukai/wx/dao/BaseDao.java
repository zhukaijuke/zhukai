package com.zhukai.wx.dao;

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

	int deleteBatch(List<Long> list);

	T findById(Long id);

	List<T> findPageInfo(Map<String, Object> map);
	
	long countTotal(Map<String, Object> map);

}
