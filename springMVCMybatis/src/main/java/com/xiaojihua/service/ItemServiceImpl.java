package com.xiaojihua.service;

import com.xiaojihua.mapper.ItemsMapper;
import com.xiaojihua.pojo.Items;
import com.xiaojihua.pojo.ItemsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<Items> getItemsList() {
        // 生成一个空的itemsExample代表没有查询条件
        // example类的使用可以参考笔记里的使用说明
        ItemsExample itemsExample = new ItemsExample();
        List<Items> items = itemsMapper.selectByExample(itemsExample);
        return items;
    }

    @Override
    public Items getItemById(int id) {
        Items items = itemsMapper.selectByPrimaryKey(id);
        return items;
    }

    @Override
    public void update(Items items) {
        itemsMapper.updateByPrimaryKeySelective(items);
    }
}
