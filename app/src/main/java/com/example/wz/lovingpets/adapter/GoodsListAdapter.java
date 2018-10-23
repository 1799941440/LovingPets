package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.widget.NumberPickerView;
import com.example.wz.lovingpets.widget.PriceView;

import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter {

    private List<GoodsDetailInfo> list;
    private Context context;
    private Integer shoppingcartId;
    private LayoutInflater layoutInflater;

    public GoodsListAdapter(List<GoodsDetailInfo> list, Context context,Integer shoppingcartId) {
        this.list = list;
        this.shoppingcartId = shoppingcartId;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_goodslist,parent,false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final GoodsDetailInfo data = list.get(position);
        ((GoodsViewHolder) holder).tv_classify.setText(data.getClassify());
        ((GoodsViewHolder) holder).tv_shopName.setText(data.getShopName());
        ((GoodsViewHolder) holder).tv_sales.setText("售出："+ data.getSales());
        ((GoodsViewHolder) holder).tv_count.setText("库存："+ data.getCount());
        ((GoodsViewHolder) holder).tv_goodsName.setText(data.getName());
        ((GoodsViewHolder) holder).priceView.setPrice(data.getPrice());
        ((GoodsViewHolder) holder).priceView.setPrice_type("￥");
        Glide.with(context)
                .load(data.getImage())
                .into(((GoodsViewHolder) holder).iv_image);
        ((GoodsViewHolder) holder).number.setMaxValue(40>=data.getCount()?data.getCount():40) //最大输入值，也就是限量，默认无限大
                .setCurrentInventory(data.getCount()) // 当前的库存
                .setMinDefaultNum(1)  // 最小限定量
                .setCurrentNum(1);  // 当前数量
        ((GoodsViewHolder) holder).iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer count = ((GoodsViewHolder) holder).number.getNumText();
                ShoppingCartDetail s= new ShoppingCartDetail(0,shoppingcartId,data.getId(),count,
                data.getPrice(),data.getPrice()*count,data.getShopId());
                EventBusUtils.sendEvent(new Event<ShoppingCartDetail>(0x123,s));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class GoodsViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_goodsName,tv_count,tv_sales,tv_shopName,tv_classify;
        public PriceView priceView;
        public NumberPickerView number;
        public ImageView iv_image,iv_add;
        public GoodsViewHolder(View view) {
            super(view);
            tv_classify = view.findViewById(R.id.goodslist_tv_classify);
            tv_sales = view.findViewById(R.id.goodslist_tv_sales);
            tv_count = view.findViewById(R.id.goodslist_tv_count);
            tv_shopName = view.findViewById(R.id.goodslist_tv_shopName);
            tv_goodsName = view.findViewById(R.id.goodslist_tv_goodsName);
            priceView = view.findViewById(R.id.goodslist_price);
            iv_image = view.findViewById(R.id.goodslist_iv_image);
            iv_add = view.findViewById(R.id.goodslist_iv_add);
            number = view.findViewById(R.id.goodslist_number_picker);
        }
    }
}
