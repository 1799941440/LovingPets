package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.entity.CollectGoodsInfo;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.widget.PriceView;

import java.util.List;

public class CollectGoodsAdapter extends RecyclerView.Adapter<CollectGoodsAdapter.GoodsHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<CollectGoodsInfo> list;

    public CollectGoodsAdapter(Context context, List<CollectGoodsInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public GoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsHolder(inflater.inflate(R.layout.item_collect_goods,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsHolder holder, int position) {
        CollectGoodsInfo data = list.get(position);
        holder.tv_goodsName.setText(data.getGoodsName());
        holder.tv_shopName.setText(data.getShopName());
        holder.pv.setPrice(data.getPrice());
        ImageUtil.loadNetImage(holder.iv,data.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GoodsHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tv_goodsName,tv_shopName;
        private PriceView pv;

        public GoodsHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.item_collect_goods_iv);
            tv_goodsName = view.findViewById(R.id.item_collect_goods_goodsName);
            tv_shopName = view.findViewById(R.id.item_collect_goods_shopName);
            pv = view.findViewById(R.id.item_collect_goods_pv);
        }
    }
}
