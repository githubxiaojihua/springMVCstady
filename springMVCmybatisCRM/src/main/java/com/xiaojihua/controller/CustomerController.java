package com.xiaojihua.controller;

import com.xiaojihua.pojo.BaseDict;
import com.xiaojihua.pojo.Customer;
import com.xiaojihua.pojo.QueryVo;
import com.xiaojihua.service.BaseDictService;
import com.xiaojihua.service.CustomerService;
import com.xiaojihua.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CustomerController {

    @Value("${base_dict.source}")
    private String customerSourceCode;
    @Value("${base_dict.industry}")
    private String customerIndustryCode;
    @Value("${base_dict.level}")
    private String customerLevelCode;
    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/list")
    public String list(QueryVo queryVo, Model model) throws UnsupportedEncodingException {
        // 初始化页面下拉框数据
        List<BaseDict> sourceList = baseDictService.findByTypeCode(customerSourceCode);
        List<BaseDict> industryList = baseDictService.findByTypeCode(customerIndustryCode);
        List<BaseDict> levelList = baseDictService.findByTypeCode(customerLevelCode);
        // 把列表设置到页面上
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("industryList",industryList);
        model.addAttribute("levelList",levelList);

        if(queryVo.getCurrentPage()==0){
            queryVo.setCurrentPage(1);
        }
        if(queryVo.getPageSize()==0){
            queryVo.setPageSize(5);
        }
        queryVo.setStart((queryVo.getCurrentPage()-1)*queryVo.getPageSize());

        // 回显数据
        model.addAttribute("custSource", queryVo.getCustSource());
        model.addAttribute("custIndustry", queryVo.getCustIndustry());
        model.addAttribute("custLevel", queryVo.getCustLevel());

        if(queryVo.getCustName()!=null && !queryVo.getCustName().equals("")){
            String custName = new String(queryVo.getCustName().getBytes("iso-8859-1"),"utf-8");
            model.addAttribute("custName", custName);
            queryVo.setCustName(custName);
        }

        // 获取分页数据
        PageBean pageBean = customerService.findByPage(queryVo);
        model.addAttribute("page", pageBean);

        return "customer";
    }

    // id 可以通过前台的json对象data:{"id":id}传递过来，而非json字符串'{"id":id}'，json字符串需要用到@RquestBody
    @RequestMapping("/edit")
    @ResponseBody
    public Customer edit(long id){
        return customerService.findById(id);
    }

    @RequestMapping("/update")
    @ResponseBody //作用 把对象转成json字符串，并且回写浏览器
    public boolean update(Customer customer){
        try {
            customerService.update(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping("/delete")
    @ResponseBody //作用 把对象转成json字符串，并且回写浏览器
    public boolean delete(long id){
        try {
            customerService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
