package com.zhukai.wx.dao;

import com.zhukai.wx.entity.CommonUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户表
 * 
 * @author zhukai
 * @email kzhu@today36524.com.cn
 * @date 2018-08-28 21:58:47
 */
@Mapper
@Repository
public interface CommonUserDao extends BaseDao<CommonUser> {
	
}
