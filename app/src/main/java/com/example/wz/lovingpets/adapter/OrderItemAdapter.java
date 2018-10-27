package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.entity.OrderDetail;

import java.util.List;

public class OrderItemAdapter extends Adapter<OrderItemAdapter.OrderItemHolder> {

    private Context context;
    private List<OrderDetail> list;
    private LayoutInflater layoutInflater;

    public OrderItemAdapter(Context context, List<OrderDetail> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemHolder(layoutInflater.inflate(R.layout.item_order_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        OrderDetail data = list.get(position);
        holder.tv_shopName.setText(data.getShopName());
        holder.tv_goodsName.setText(data.getGoodsName());
        holder.tv_price.setText("￥"+data.getPrice());
        holder.tv_count.setText("x"+data.getCount());
        holder.tv_total.setText("总计："+data.getAmount());
        Glide.with(context).load(data.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderItemHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tv_shopName,tv_goodsName,tv_price,tv_count,tv_total;
        public OrderItemHolder(View view) {
            super(view);
            image = view.findViewById(R.id.orderDetail_item_image);
            tv_shopName = view.findViewById(R.id.orderDetail_item_shopName);
            tv_goodsName = view.findViewById(R.id.orderDetail_item_goodsName);
            tv_price = view.findViewById(R.id.orderDetail_item_price);
            tv_count = view.findViewById(R.id.orderDetail_item_count);
            tv_total = view.findViewById(R.id.orderDetail_item_total);
        }
    }
}
