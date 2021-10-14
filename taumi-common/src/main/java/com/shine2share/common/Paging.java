package com.shine2share.common;

import java.io.Serializable;
import java.util.List;

public class Paging<T> implements Serializable {
    private List<T> items;
    private int pageSize; // số bản ghi / trang
    private int pageNum; // số trang
    private long totalItem;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }
}