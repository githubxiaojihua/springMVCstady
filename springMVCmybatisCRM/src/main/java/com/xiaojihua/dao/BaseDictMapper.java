package com.xiaojihua.dao;

import com.xiaojihua.pojo.BaseDict;

import java.util.List;

public interface BaseDictMapper {
    List<BaseDict> findByTypeCode(String typeCode);

}
