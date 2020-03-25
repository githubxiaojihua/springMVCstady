package com.xiaojihua.service.impl;

import com.xiaojihua.dao.CustomerMapper;
import com.xiaojihua.pojo.Customer;
import com.xiaojihua.pojo.QueryVo;
import com.xiaojihua.service.CustomerService;
import com.xiaojihua.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public PageBean findByPage(QueryVo queryVo) {
        PageBean pageBean = new PageBean<Customer>();
        List<Customer> list = customerMapper.findByPage(queryVo);
        int totalCount = customerMapper.findTotalCount(queryVo);
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        pageBean.setList(list);
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public Customer findById(long id) {
        return customerMapper.findById(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.update(customer);
    }

    @Override
    public void delete(long id) {
        customerMapper.delete(id);
    }
}
