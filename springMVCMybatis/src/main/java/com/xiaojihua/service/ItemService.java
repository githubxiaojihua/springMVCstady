package com.xiaojihua.service;

import com.xiaojihua.pojo.Items;

import java.util.List;

public interface ItemService {
    List<Items> getItemsList();
    Items getItemById(int id);
    void update(Items items);
}
