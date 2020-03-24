package com.xiaojihua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session){
        session.setAttribute("user",userName);
        return "success";
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session){
        // session过期
        session.invalidate();
        return "redirect:/list.action";
    }
}
