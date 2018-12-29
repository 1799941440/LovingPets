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
import com.example.wz.lovingpets.activity.ThemeDetailActivity;
import com.example.wz.lovingpets.entity.ThemeInfo;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.StringUtils;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.VH> {

    private int size;
    private Context context;
    private List<ThemeInfo> list;
    private LayoutInflater inflater;

    public ThemeAdapter(List<ThemeInfo> list, Context context) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }
    public ThemeAdapter(List<ThemeInfo> list, Context context,int size) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.size = size;
    }

    @Override
    public int getItemViewType(int position) {
        if(StringUtils.getImgs(list.get(position).getContent()).size()>=3){
            return 2;
        }else if(StringUtils.getImgs(list.get(position).getContent()).size() == 0){
            return 0;
        }else{
            return 1;
        }
    }
    
    @NonNull
    @Override
    public ThemeAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 2){
            return new ThemeAdapter.VH3(inflater.inflate(R.layout.item_collect_theme3,parent,false));
        }else{
            return new ThemeAdapter.VH1(inflater.inflate(R.layout.item_collect_theme1,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeAdapter.VH holder, int position) {
        final ThemeInfo data = list.get(position);
        List<String> images = StringUtils.getImgs(data.getContent());
        if(holder instanceof VH1){
            if(images.size() == 0){
                ((VH1) holder).iv.setVisibility(View.GONE);
            }else{
                ImageUtil.loadLocalImage(((VH1) holder).iv,images.get(0));
            }
            ((VH1) holder).tv_title.setText(data.getTitle());
            ((VH1) holder).tv_content.setText(data.getContent());
            ((VH1) holder).tv_author.setText(data.getUserName());
            ((VH1) holder).tv_date.setText(DateUtil.DateToString(data.getPushTime()));
            ((VH1) holder).tv_comment.setText(data.getComment()+"");
            ((VH1) holder).tv_collect.setText(data.getCollection()+"");
            ((VH1) holder).ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThemeDetailActivity.class);
                    intent.putExtra("id",data.getId());
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof VH3){
            ((VH3) holder).tv_title.setText(data.getTitle());
            ((VH3) holder).tv_content.setText(data.getContent());
            ((VH3) holder).tv_author.setText(data.getUserName());
            ((VH3) holder).tv_date.setText(DateUtil.DateToString(data.getPushTime()));
            ((VH3) holder).tv_comment.setText(data.getComment()+"");
            ((VH3) holder).tv_collect.setText(data.getCollection()+"");
            ((VH3) holder).tv_count.setText(images.size()+"图");
            ((VH3) holder).ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThemeDetailActivity.class);
                    intent.putExtra("id",data.getId());
                    context.startActivity(intent);
                }
            });
            ImageUtil.loadLocalImage(((VH3) holder).iv1,images.get(0));
            ImageUtil.loadLocalImage(((VH3) holder).iv2,images.get(1));
            ImageUtil.loadLocalImage(((VH3) holder).iv3,images.get(2));
        }
    }

    @Override
    public int getItemCount() {
        if(size == 0){
            return list.size();
        }else{
            return size;
        }
    }

    public class VH extends RecyclerView.ViewHolder{
        public VH(View view) {
            super(view);
        }
    }

    public class VH1 extends ThemeAdapter.VH {

        ImageView iv;
        LinearLayout ll_root;
        TextView tv_title,tv_content,tv_author,tv_date,tv_comment,tv_collect;
        public VH1(View view) {
            super(view);
            iv = view.findViewById(R.id.item_collect_theme0_iv);
            tv_title = view.findViewById(R.id.item_collect_theme0_title);
            tv_content = view.findViewById(R.id.item_collect_theme0_content);
            tv_author = view.findViewById(R.id.item_collect_theme0_author);
            tv_date = view.findViewById(R.id.item_collect_theme0_date);
            tv_comment = view.findViewById(R.id.item_collect_theme0_comment);
            tv_collect = view.findViewById(R.id.item_collect_theme0_collect);
            ll_root = view.findViewById(R.id.item_collect_theme0_root);
        }
    }

    public class VH3 extends ThemeAdapter.VH {

        LinearLayout ll_root;
        ImageView iv1,iv2,iv3;
        TextView tv_title,tv_content,tv_author,tv_date,tv_comment,tv_collect,tv_count;
        public VH3(View view) {
            super(view);
            iv1 = view.findViewById(R.id.item_collect_theme1_iv1);
            iv2 = view.findViewById(R.id.item_collect_theme1_iv2);
            iv3 = view.findViewById(R.id.item_collect_theme1_iv3);
            tv_title = view.findViewById(R.id.item_collect_theme1_title);
            tv_content = view.findViewById(R.id.item_collect_theme1_content);
            tv_author = view.findViewById(R.id.item_collect_theme1_author);
            tv_date = view.findViewById(R.id.item_collect_theme1_date);
            tv_comment = view.findViewById(R.id.item_collect_theme1_comment);
            tv_collect = view.findViewById(R.id.item_collect_theme1_collect);
            tv_count = view.findViewById(R.id.item_collect_theme1_imgCount);
            ll_root = view.findViewById(R.id.item_collect_theme1_root);
        }
    }
}
