package com.example.wz.lovingpets.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.CommentServerActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.ServiceOrderInfo;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.StringUtils;

import java.util.List;

public class ServerOrderAdapter extends RecyclerView.Adapter<ServerOrderAdapter.SOHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ServiceOrderInfo> list;

    public ServerOrderAdapter(Context context, List<ServiceOrderInfo> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public SOHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SOHolder(inflater.inflate(R.layout.item_server_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SOHolder holder, int position) {
        ServiceOrderInfo data = list.get(position);
        holder.tv_id.setText("订单编号："+data.getId());
        setState(holder.tv_state,data.getState());
        ImageUtil.loadNetImage(holder.image,data.getImages());
        holder.tv_name.setText(data.getServiceName());
        holder.tv_shopName.setText(data.getShopName());
        holder.tv_address.setText(data.getShopAddress());
        holder.tv_payWay.setText("支付方式："+(data.getPayWay() ==  0 ? "全部付清" : "待定的金额"));
        holder.tv_payed.setText("已付金额："+(data.getFinalPrice() +data.getOrderPrice()));
        if(data.getState() == 2){
            holder.tv_payed.setText(holder.tv_payed.getText()+"  待付金额："+data.getFinalPrice());
        }
        holder.tv_orderTime.setText("预约时间："+DateUtil.DateToString(data.getOrderDate()));
        holder.tv_serverTime.setText("服务时间："+DateUtil.DateToString(data.getServerDate()));
        holder.tv_orderMsg.setText("用户预约留言："+data.getOrderMessage());
        if(!StringUtils.isEmpty(data.getShopMessage())){
            holder.tv_shopMsg.setText("店家受理留言："+data.getShopMessage());
        }else{
            holder.tv_shopMsg.setText("店家受理留言：无");
        }
        setBTs(holder.bt_left,holder.bt_right,data);
    }


    private void setBTs(final Button bt_left, final Button bt_right, final ServiceOrderInfo s) {
        if(s.getState() == 0){
            bt_left.setText("取消服务");
            bt_right.setVisibility(View.GONE);
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.CANCEL_SERVER_ORDER,s.getId()));
                }
            });
        }else if(s.getState() == 1){
            bt_left.setText("延后服务");
            bt_right.setVisibility(View.GONE);
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.DELAY_SERVER_ORDER,s.getId()));
                }
            });
        }else if(s.getState() == 2){
            bt_left.setText("立即支付");
            bt_right.setVisibility(View.GONE);
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.PAY_SERVER_ORDER,s.getId()));
                }
            });
        }else if(s.getState() == 3 || s.getState() == 5){
            bt_left.setText("删除订单");
            bt_right.setVisibility(View.GONE);
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.DEL_SERVER_ORDER,s.getId()));
                }
            });
        }else if(s.getState() == 4){
            bt_left.setText("删除订单");
            bt_right.setVisibility(View.VISIBLE);
            bt_right.setText("评价订单");
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.DEL_SERVER_ORDER,s.getId()));
                }
            });
            bt_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentServerActivity.class);
                    intent.putExtra("serviceId",s.getServiceId());
                    intent.putExtra("orderId",s.getId());
                    intent.putExtra("imageUrl",s.getImages());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void setState(TextView tv_state, byte state) {
        if(state == 0){
            tv_state.setTextColor(Color.parseColor("#ffa000"));
            tv_state.setText("待受理");
        }else if(state == 1){
            tv_state.setTextColor(Color.parseColor("#ffa000"));
            tv_state.setText("待服务/服务中");
        }else if(state == 2){
            tv_state.setTextColor(Color.parseColor("#ffa000"));
            tv_state.setText("待支付");
        }else if(state == 3){
            tv_state.setTextColor(Color.parseColor("#ff0000"));
            tv_state.setText("服务取消");
        }else if(state == 4){
            tv_state.setTextColor(Color.parseColor("#00ff00"));
            tv_state.setText("待评价");
        }else if(state == 5){
            tv_state.setTextColor(Color.parseColor("#00ff00"));
            tv_state.setText("服务完成");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SOHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView tv_id,tv_state,tv_name,tv_shopName,tv_address,tv_payWay,
        tv_payed,tv_orderTime,tv_serverTime,tv_orderMsg,tv_shopMsg;
        Button bt_left,bt_right;

        public SOHolder(View view) {
            super(view);
            image = view.findViewById(R.id.item_server_order_image);
            tv_id = view.findViewById(R.id.item_server_order_id);
            tv_state = view.findViewById(R.id.item_server_order_state);
            tv_name = view.findViewById(R.id.item_server_order_name);
            tv_shopName = view.findViewById(R.id.item_server_order_shopName);
            tv_address = view.findViewById(R.id.item_server_order_address);
            tv_payWay = view.findViewById(R.id.item_server_order_payWay);
            tv_payed = view.findViewById(R.id.item_server_order_payed);

            tv_orderTime = view.findViewById(R.id.item_server_order_orderTime);
            tv_serverTime = view.findViewById(R.id.item_server_order_serverTime);
            tv_orderMsg = view.findViewById(R.id.item_server_order_orderMessage);
            tv_shopMsg = view.findViewById(R.id.item_server_order_shopMessage);
            bt_left = view.findViewById(R.id.item_server_order_left);
            bt_right = view.findViewById(R.id.item_server_order_right);
        }
    }
}
