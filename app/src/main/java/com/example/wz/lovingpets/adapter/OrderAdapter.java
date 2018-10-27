package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.OrderInfo;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<OrderInfo> list;

    public OrderAdapter(Context context, List<OrderInfo> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(layoutInflater.inflate(R.layout.item_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        final OrderInfo data = list.get(position);
        holder.tv_id.setText(data.getId()+"");
        holder.tv_state.setText(getState(data.getOrderState()));
        holder.tv_total.setText(data.getAmount()+"");
        if(data.getOrderState() == 2){
            //2-待收货
            holder.tv_state.setTextColor(Color.parseColor("#00ff00"));
            holder.bt_right.setText("确认收货");
            holder.bt_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<List>(EventCodes.RECEIVE_ORDER,Arrays.asList(data.getId(),3)));
                }
            });
        }else if(data.getOrderState() == 3){
            //3-交易成功
            holder.bt_right.setVisibility(View.GONE);
            holder.tv_state.setTextColor(Color.parseColor("#00ff00"));
        }else if(data.getOrderState() == 1){
            //1-订单失效
            holder.bt_right.setVisibility(View.GONE);
            holder.tv_state.setTextColor(Color.parseColor("#ff0000"));
        }else if(data.getOrderState() == 0){
            //待付款
            holder.bt_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<List>(EventCodes.PAY_ORDER,Arrays.asList(data.getId(),2)));
                }
            });
            holder.tv_state.setTextColor(Color.parseColor("#ffa000"));
        }
        holder.bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event<List>(EventCodes.DEL_ORDER, Arrays.asList(data.getId())));
            }
        });
        holder.rv.setAdapter(new OrderItemAdapter(context,data.getOrderDetails()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        public RecyclerView rv;
        public Button bt_left, bt_right;
        public TextView tv_id,tv_state,tv_total;
        public OrderHolder(View view) {
            super(view);
            rv = view.findViewById(R.id.order_item_rv);
            bt_left = view.findViewById(R.id.order_item_left);
            bt_right = view.findViewById(R.id.order_item_right);
            tv_id = view.findViewById(R.id.order_item_id);
            tv_state = view.findViewById(R.id.order_item_state);
            tv_total = view.findViewById(R.id.order_item_total);
        }

    }
    private String getState(int orderState) {
        if(orderState == 0){
            return "待付款";
        }else if(orderState == 1){
            return "订单失效";
        }else if(orderState == 2){
            return "待收货";
        }else if(orderState == 3){
            return "交易成功";
        }else{
            return "";
        }
    }
}
