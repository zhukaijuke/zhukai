package com.zhukai.wx.controller;

import com.zhukai.wx.entity.CommonUser;
import com.zhukai.wx.service.CommonUserService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @GetMapping("/get/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        CommonUser user = commonUserService.getById(id);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "demo";
    }

    @RequestMapping("/print")
    public void print(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jasperFilePath = "C:/Users/lenovo/Desktop/report1.jasper";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");

        byte[] bytes = JasperRunManager.runReportToPdf(jasperFilePath, new HashMap<>(), new JREmptyDataSource());
        OutputStream out = null;
        out = response.getOutputStream();
        out.write(bytes);
        out.flush();
        out.close();
    }

    @RequestMapping("/testParam")
    @ResponseBody
    public String testParam(Long skuId, String skuName, int skuQty, Boolean flag) {
        System.out.println(skuId);
        System.out.println(skuName);
        System.out.println(skuQty);
        System.out.println(flag);
        return skuId + "-" + skuName + "-" + skuQty + "-" + flag;
    }

}
