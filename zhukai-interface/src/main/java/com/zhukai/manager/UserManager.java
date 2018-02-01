package com.zhukai.manager;

import com.zhukai.model.User;

public interface UserManager {
	
	boolean checkUserByUserName(String userName);
	
	void insertNewUser(User user);
	
	User findUserByUserNameAndPassword(String userName, String password);
}
