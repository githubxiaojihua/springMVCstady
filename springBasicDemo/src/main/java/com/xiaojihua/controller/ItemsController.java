package com.xiaojihua.controller;


import com.xiaojihua.pojo.Items;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建Controller的传统方式：继承自Controller，但是在开发中经常使用的方法
 * 还是使用注解的方式
 */
public class ItemsController implements Controller {

    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        List<Items> itemsList = new ArrayList<>();

        //商品列表
        Items items_1 = new Items();
        items_1.setName("联想笔记本");
        items_1.setPrice(6000f);
        items_1.setDetail("ThinkPad T430 联想笔记本电脑！");

        Items items_2 = new Items();
        items_2.setName("苹果手机");
        items_2.setPrice(5000f);
        items_2.setDetail("iphone6苹果手机！");

        itemsList.add(items_1);
        itemsList.add(items_2);
        //创建modelandView对象
        ModelAndView modelAndView = new ModelAndView();
        //添加model
        modelAndView.addObject("itemList", itemsList);
        //添加视图
        modelAndView.setViewName("/WEB-INF/jsp/itemList.jsp");
        return modelAndView;
    }
}
