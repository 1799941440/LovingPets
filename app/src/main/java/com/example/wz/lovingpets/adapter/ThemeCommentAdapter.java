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
import com.example.wz.lovingpets.entity.ThemeCommentInfo;
import com.example.wz.lovingpets.entity.ThemeInfo;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;

import java.util.List;

public class ThemeCommentAdapter extends RecyclerView.Adapter<ThemeCommentAdapter.TCVH> {

    private int ownerId;
    private LayoutInflater inflater;
    private List<ThemeCommentInfo> list;

    public ThemeCommentAdapter(Context context,List<ThemeCommentInfo> list,int ownweId) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.ownerId = ownweId;
    }

    @NonNull
    @Override
    public TCVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TCVH(inflater.inflate(R.layout.item_theme_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TCVH holder, int position) {
        ThemeCommentInfo data = list.get(position);
        ImageUtil.loadNetImage(holder.iv_head,data.getIcon());
        holder.iv_sex.setImageResource(data.getSex().equals("男")?R.drawable.ic_age_male:R.drawable.ic_age_female);
        holder.ll_bg.setActivated(data.getSex().equals("女"));
        holder.tv_floor.setText((position+1)+"楼");
        holder.tv_age.setText(""+data.getAge());
        holder.tv_time.setText(DateUtil.DateToString(data.getReplyTime()));
        holder.tv_comment.setText(data.getReply());
        holder.tv_name.setText(data.getUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TCVH extends RecyclerView.ViewHolder{

        LinearLayout ll_bg;
        ImageView iv_sex,iv_head;
        TextView tv_name,tv_age,tv_floor,tv_time,tv_comment;
        public TCVH(View view) {
            super(view);
            ll_bg = view.findViewById(R.id.item_theme_comment_ll_bg);
            iv_sex = view.findViewById(R.id.item_theme_comment_sex);
            iv_head = view.findViewById(R.id.item_theme_comment_icon);
            tv_name = view.findViewById(R.id.item_theme_comment_name);
            tv_age = view.findViewById(R.id.item_theme_comment_age);
            tv_floor = view.findViewById(R.id.item_theme_comment_floor);
            tv_time = view.findViewById(R.id.item_theme_comment_time);
            tv_comment = view.findViewById(R.id.item_theme_comment_comment);
        }
    }
}
