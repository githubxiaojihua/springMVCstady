package com.xiaojihua.service;

import com.xiaojihua.pojo.BaseDict;
import com.xiaojihua.pojo.Customer;
import com.xiaojihua.pojo.QueryVo;
import com.xiaojihua.utils.PageBean;

import java.util.List;

public interface CustomerService {
    PageBean findByPage(QueryVo queryVo);
    Customer findById(long id);
    void update(Customer customer);
    void delete(long id);
}
