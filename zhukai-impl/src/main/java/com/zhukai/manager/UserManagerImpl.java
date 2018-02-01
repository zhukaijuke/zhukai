package com.zhukai.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhukai.dao.UserDao;
import com.zhukai.model.User;

@Transactional
@Service("userManager")
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean checkUserByUserName(String userName) {
		int count = userDao.checkUserByUserName(userName);
		return count > 0;
	}

	@Override
	public void insertNewUser(User user) {
		System.out.println(userDao.insert(user));
	}

	@Override
	public User findUserByUserNameAndPassword(String userName, String password) {
		return userDao.findUserByUserNameAndPassword(userName, password);
	}

}
