package com.example.wz.lovingpets.entity;

import java.util.ArrayList;

public class ListResponse<T> {

    private boolean success=true;
    private String msg;
    private long total;
    private int pageSize;
    private ArrayList<T> rows;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<T> getRows() {
        return rows;
    }

    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
    }
}
