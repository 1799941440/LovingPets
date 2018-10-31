package com.example.wz.lovingpets.widget;

import java.util.ArrayList;
import java.util.List;

public class SingleDialogBuilder {
    private String title;
    private List<String> list = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public SingleDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getList() {
        return list;
    }

    public SingleDialogBuilder setList(List<String> list) {
        this.list = list;
        return this;
    }
    public SingleDialog build(){
        return new SingleDialog(this);
    }
}
