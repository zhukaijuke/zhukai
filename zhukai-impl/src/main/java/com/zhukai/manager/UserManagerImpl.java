package com.zhukai.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhukai.common.redis.CacheManager;
import com.zhukai.constant.RedisKeyConstant;
import com.zhukai.dao.UserDao;
import com.zhukai.model.User;

@Transactional
@Service("userManager")
public class UserManagerImpl implements UserManager {
	
	protected static final Logger log = LoggerFactory.getLogger(UserManagerImpl.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private CacheManager cacheManager;
	
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
		String redisKey = RedisKeyConstant.CACHE_USER + "-" + userName+ "-" + password;
		
		User user = cacheManager.getObject(redisKey);
		if (user == null) {
			log.info("findUserByUserNameAndPassword by mysql userName:{} password:{}", userName, password);
			user = userDao.findUserByUserNameAndPassword(userName, password);
			cacheManager.setObject(redisKey, user, RedisKeyConstant.CACHE_ONE_MINUTE);
		}
		return user;
	}

}
