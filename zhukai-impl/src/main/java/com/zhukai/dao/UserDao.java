package com.zhukai.dao;

import org.apache.ibatis.annotations.Param;

import com.zhukai.model.User;

public interface UserDao {

	int checkUserByUserName(String userName);

	long insert(User user);

	User findUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

}
