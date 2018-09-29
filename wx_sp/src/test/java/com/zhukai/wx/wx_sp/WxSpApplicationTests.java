package com.zhukai.wx.wx_sp;
import java.util.Date;

import com.zhukai.wx.dao.CommonUserDao;
import com.zhukai.wx.entity.CommonUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxSpApplicationTests {

    @Autowired
    private CommonUserDao commonUserDao;

	@Test
	public void contextLoads() {
        /*List<CommonUser> list = new ArrayList<>();
        CommonUser user = new CommonUser();
        user.setLoginName("test");
        user.setUserName("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setPhone("test");
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastModifyTime(new Date());
        user.setLifeCycle(1);
        user.setRemark("test");

        CommonUser user2 = new CommonUser();
        user2.setLoginName("test2");
        user2.setUserName("test2");
        user2.setPassword("test2");
        user2.setEmail("test2");
        user2.setPhone("test2");
        user2.setCreateTime(new Date());
        user2.setLastLoginTime(new Date());
        user2.setLastModifyTime(new Date());
        user2.setLifeCycle(1);
        user2.setRemark("test2");
        list.add(user);
        list.add(user2);

        commonUserDao.saveBatch(list);*/
        List<Long> list = new ArrayList<>();
        list.add(3L);
        list.add(4L);
        commonUserDao.deleteBatch(list);
	}

}
