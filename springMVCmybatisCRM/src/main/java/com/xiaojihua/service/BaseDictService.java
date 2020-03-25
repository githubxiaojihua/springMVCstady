package com.xiaojihua.service;

import com.xiaojihua.pojo.BaseDict;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BaseDictService {
    List<BaseDict> findByTypeCode(String typeCode);
}
