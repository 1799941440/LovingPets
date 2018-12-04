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
import com.example.wz.lovingpets.entity.ServiceCommentInfo;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;

import java.util.List;

public class OrderCommentAdapter extends RecyclerView.Adapter<OrderCommentAdapter.OCHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ServiceCommentInfo> list;

    public OrderCommentAdapter(Context context, List<ServiceCommentInfo> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OCHolder(inflater.inflate(R.layout.item_server_order_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OCHolder holder, int position) {
        ServiceCommentInfo data = list.get(position);
        ImageUtil.loadRoundImage(holder.iv_head,data.getIcon());
        holder.tv_name.setText(data.getUserName());
        holder.tv_age.setText(""+data.getAge());
        holder.tv_time.setText(DateUtil.DateToString(data.getCommentTime()));
        holder.tv_star.setText("评分："+data.getStar());
        holder.iv_sex.setImageResource(data.getSex().equals("男")?R.drawable.ic_age_male:R.drawable.ic_age_female);
        holder.ll_bg.setActivated(data.getSex().equals("女"));
        holder.tv_comment.setText(data.getComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OCHolder extends RecyclerView.ViewHolder{

        ImageView iv_head,iv_sex;
        LinearLayout ll_bg;
        TextView tv_name,tv_age,tv_time,tv_star,tv_comment;
        public OCHolder(View view) {
            super(view);
            iv_head = view.findViewById(R.id.item_server_comment_head);
            iv_sex = view.findViewById(R.id.item_server_comment_sex);
            ll_bg = view.findViewById(R.id.item_server_comment_ll_bg);
            tv_name = view.findViewById(R.id.item_server_comment_name);
            tv_age = view.findViewById(R.id.item_server_comment_age);
            tv_time = view.findViewById(R.id.item_server_comment_time);
            tv_star = view.findViewById(R.id.item_server_comment_star);
            tv_comment = view.findViewById(R.id.item_server_comment_comment);
        }
    }
}
