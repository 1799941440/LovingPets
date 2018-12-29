package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.utils.ImageUtil;

import java.util.List;

public class TopServiceAdapter extends RecyclerView.Adapter<TopServiceAdapter.TSHolder> {

    private List<ServiceInfo> list;
    private LayoutInflater inflater;

    public TopServiceAdapter(List<ServiceInfo> list, Context context) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TSHolder(inflater.inflate(R.layout.item_top_service,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TSHolder holder, int position) {
        ServiceInfo data = list.get(position);
        holder.tv_name.setText(data.getServiceName());
        ImageUtil.loadNetImage(holder.image,data.getImages());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TSHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView tv_name;
        LinearLayout root;
        public TSHolder(View view) {
            super(view);
            image = view.findViewById(R.id.item_top_server_image);
            tv_name = view.findViewById(R.id.item_top_server_name);
            root = view.findViewById(R.id.item_top_server_root);
        }
    }
}
