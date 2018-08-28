package com.zhukai.wx.controller;

import com.zhukai.wx.entity.CommonUser;
import com.zhukai.wx.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @RequestMapping("/get/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        CommonUser user = commonUserService.getById(id);
        model.addAttribute("user", user);
        return "index";
    }

}
