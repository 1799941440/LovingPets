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
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.utils.ImageUtil;

import java.util.List;

public class TopGoodsAdapter extends RecyclerView.Adapter<TopGoodsAdapter.TGHolder> {

    private List<GoodsDetailInfo> list;
    private LayoutInflater inflater;

    public TopGoodsAdapter(List<GoodsDetailInfo> list, Context context) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TGHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TGHolder(inflater.inflate(R.layout.item_top_goods,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TGHolder holder, int position) {
        GoodsDetailInfo data = list.get(position);
        holder.tv_name.setText(data.getName());
        ImageUtil.loadNetImage(holder.image,data.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TGHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView tv_name;
        LinearLayout root;
        public TGHolder(View view) {
            super(view);
            image = view.findViewById(R.id.item_top_goods_image);
            tv_name = view.findViewById(R.id.item_top_goods_name);
            root = view.findViewById(R.id.item_top_goods_root);
        }
    }
}
