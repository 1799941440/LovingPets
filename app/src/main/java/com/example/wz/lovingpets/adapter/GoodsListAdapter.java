package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.widget.AddToCartDialog;
import com.example.wz.lovingpets.widget.NumberPickerView;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;

import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private List<GoodsDetailInfo> list;
    private Context context;
    private Integer shoppingcartId;
    private LayoutInflater layoutInflater;

    public GoodsListAdapter(List<GoodsDetailInfo> list, Context context, Integer shoppingcartId) {
        this.list = list;
        this.shoppingcartId = shoppingcartId;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_goodslist, parent, false);
        return new GoodsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, final int position) {
        final GoodsDetailInfo data = list.get(position);
        holder.tv_classify.setText(data.getClassify());
        holder.tv_shopName.setText(data.getShopName());
        holder.tv_sales.setText("售出：" + data.getSales());
        holder.tv_count.setText("库存：" + data.getCount());
        holder.tv_goodsName.setText(data.getName());
        holder.priceView.setPrice(data.getPrice());
        holder.priceView.setPrice_type("￥");
        Glide.with(context)
                .load(data.getImage())
                .into(holder.iv_image);
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event<Integer>(EventCodes.OPEN_GOODSDETAIL_DIALOG,position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        PriceView priceView;
        ImageView iv_image;
        LinearLayout ll_root;
        TextView tv_goodsName, tv_count, tv_sales, tv_shopName, tv_classify;

        GoodsViewHolder(View view) {
            super(view);
            tv_classify = view.findViewById(R.id.goodslist_tv_classify);
            tv_sales = view.findViewById(R.id.goodslist_tv_sales);
            tv_count = view.findViewById(R.id.goodslist_tv_count);
            tv_shopName = view.findViewById(R.id.goodslist_tv_shopName);
            tv_goodsName = view.findViewById(R.id.goodslist_tv_goodsName);
            priceView = view.findViewById(R.id.goodslist_price);
            iv_image = view.findViewById(R.id.goodslist_iv_image);
            ll_root = view.findViewById(R.id.goodslist_ll_root);
        }
    }
}
