package com.xiaojihua.service.impl;

import com.xiaojihua.dao.BaseDictMapper;
import com.xiaojihua.pojo.BaseDict;
import com.xiaojihua.service.BaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDictServiceImpl implements BaseDictService {

    @Autowired
    private BaseDictMapper baseDictMapper;

    @Override
    public List<BaseDict> findByTypeCode(String typeCode) {
        return baseDictMapper.findByTypeCode(typeCode);
    }
}
