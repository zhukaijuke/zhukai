package com.zhukai.wx.wx_sp;

import com.zhukai.wx.WxSpApplication;
import com.zhukai.wx.dao.CommonUserDao;
import com.zhukai.wx.entity.CommonUser;
import com.zhukai.wx.service.CommonUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WxSpApplication.class})
public class WxSpApplicationTests {

    @Autowired
    private CommonUserService commonUserService;

    @Test
    public void contextLoads() throws Exception {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    int num = commonUserService.updateTest2("zhukai");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
        Thread.sleep(2000);
    }

}
