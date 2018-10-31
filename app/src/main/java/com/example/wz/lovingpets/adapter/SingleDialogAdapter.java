package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wz.lovingpets.R;

import java.util.List;

public class SingleDialogAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public SingleDialogAdapter(List<String> list, Context context) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView==null){
            view = layoutInflater.inflate(R.layout.item_dialog_single,parent,false);
        }else{
            view = convertView;
        }
        TextView tv = view.findViewById(R.id.item_single_dialog_tv);
        tv.setText(getItem(position));
        return view;
    }
}
