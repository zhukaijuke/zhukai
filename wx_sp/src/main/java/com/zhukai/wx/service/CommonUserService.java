package com.zhukai.wx.service;

import com.zhukai.wx.entity.CommonUser;

import java.util.List;

/**
 * 用户表
 * 
 * @author zhukai
 * @email kzhu@today36524.com.cn
 * @date 2018-08-28 21:58:47
 */
public interface CommonUserService {

    List<CommonUser> findList(CommonUser commonUser);

    CommonUser getById(Long id);

    boolean saveOrUpdate(CommonUser commonUser);

    int updateTest2(String zhukai);
}
