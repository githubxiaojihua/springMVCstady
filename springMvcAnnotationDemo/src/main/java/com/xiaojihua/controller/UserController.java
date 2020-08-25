package com.xiaojihua.controller;

import com.xiaojihua.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/handle91")
    public String handle91(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request,Map map){
        if(bindingResult.hasErrors()){
            return "register3";
        }else{
            return "showUser";
        }
    }

    @RequestMapping(value = "/register3")
    public String register3() {
        return "register3";
    }
}
