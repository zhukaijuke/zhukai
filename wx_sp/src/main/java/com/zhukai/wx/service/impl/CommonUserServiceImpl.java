package com.zhukai.wx.service.impl;

import com.zhukai.wx.dao.CommonUserDao;
import com.zhukai.wx.entity.CommonUser;
import com.zhukai.wx.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ommonUserService")
public class CommonUserServiceImpl implements CommonUserService {

    @Autowired
    private CommonUserDao commonUserDao;

    @Override
    public List<CommonUser> findList(CommonUser commonUser) {
        return null;
    }

    @Override
    public CommonUser getById(Long id) {
        return commonUserDao.findById(id);
    }

    @Override
    public boolean saveOrUpdate(CommonUser commonUser) {
        return false;
    }

    @Override
    @Transactional
    public int updateTest2(String zhukai) {
        return commonUserDao.updateTest2(zhukai);
    }
}
