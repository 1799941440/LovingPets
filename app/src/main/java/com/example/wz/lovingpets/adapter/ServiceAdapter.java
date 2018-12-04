package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MainActivity;
import com.example.wz.lovingpets.activity.OrderServerActivity;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.widget.PriceView;
import com.google.gson.Gson;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServerVH> {

    private Context context;
    private LayoutInflater inflater;
    private List<ServiceInfo> list;

    public ServiceAdapter(Context context, List<ServiceInfo> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ServerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServerVH(inflater.inflate(R.layout.item_server_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServerVH holder, int position) {
        final ServiceInfo data = list.get(position);
        ImageUtil.loadNetImage(holder.image,data.getImages());
        holder.pv.setPrice(data.getPrice() == 0f ? data.getOrderPrice() : data.getPrice());
        holder.tv_address.setText(data.getAddress());
        holder.tv_name.setText(data.getServiceName());
        holder.tv_payWay.setText(data.getPayWay() == 0 ? "付费方式:一次性付清" : "付费方式:待定的价格");
        holder.tv_star.setText("评分"+data.getCommentStars());
        holder.tv_shopName.setText(data.getShopName());
        holder.tv_sales.setText("已预约次数："+data.getSales());
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderServerActivity.class);
                intent.putExtra("data",new Gson().toJson(data));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServerVH extends RecyclerView.ViewHolder{

        LinearLayout ll_root;
        ImageView image;
        TextView tv_name,tv_shopName,tv_star,tv_address,tv_payWay,tv_sales;
        PriceView pv;
        public ServerVH(View view) {
            super(view);
            image = view.findViewById(R.id.item_server_list_image);
            tv_name = view.findViewById(R.id.item_server_list_name);
            tv_shopName = view.findViewById(R.id.item_server_list_shopName);
            tv_star = view.findViewById(R.id.item_server_list_star);
            pv = view.findViewById(R.id.item_server_list_pv);
            tv_address = view.findViewById(R.id.item_server_list_address);
            tv_payWay = view.findViewById(R.id.item_server_list_payWay);
            tv_sales = view.findViewById(R.id.item_server_list_sales);
            ll_root = view.findViewById(R.id.item_server_list_root);
        }
    }
}
