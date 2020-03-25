package com.xiaojihua.dao;

import com.xiaojihua.pojo.Customer;
import com.xiaojihua.pojo.QueryVo;

import java.util.List;

public interface CustomerMapper {
    List<Customer> findByPage(QueryVo queryVo);
    int findTotalCount(QueryVo queryVo);
    Customer findById(long id);
    void update(Customer customer);
    void delete(long id);
}
