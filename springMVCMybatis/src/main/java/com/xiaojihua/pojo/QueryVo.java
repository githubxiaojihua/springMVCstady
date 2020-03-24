package com.xiaojihua.pojo;

import java.util.Arrays;
import java.util.List;

public class QueryVo {
    private Items items;

    private int[] ids;

    private List<Items> itemList;

    public List<Items> getItemList() {
        return itemList;
    }

    public void setItemList(List<Items> itemList) {
        this.itemList = itemList;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "QueryVo{" +
                "items=" + items +
                ", ids=" + Arrays.toString(ids) +
                ", itemList=" + itemList +
                '}';
    }
}
