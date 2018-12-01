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
import com.example.wz.lovingpets.entity.CollectThemeInfo;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.StringUtils;

import java.util.List;

public class CollectThemeAdapter extends RecyclerView.Adapter<CollectThemeAdapter.VH> {

    private Context context;
    private LayoutInflater inflater;
    private List<CollectThemeInfo> list;

    public CollectThemeAdapter(Context context, List<CollectThemeInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(StringUtils.getImgs(list.get(position).getContent()).size()>3){
            return 1;
        }else{
            return 0;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            return new VH1(inflater.inflate(R.layout.item_collect_theme0,parent,false));
        }else{
            return new VH3(inflater.inflate(R.layout.item_collect_theme1,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CollectThemeInfo data = list.get(position);
        List<String> images = StringUtils.getImgs(data.getContent());
        if(holder instanceof VH1){
            ((VH1) holder).tv_title.setText(data.getTitle());
            ((VH1) holder).tv_content.setText(data.getContent());
            ((VH1) holder).tv_author.setText(data.getUserName());
            ((VH1) holder).tv_date.setText(DateUtil.DateToString(data.getPushTime()));
            ((VH1) holder).tv_comment.setText(data.getCommentTimes()+"");
            ((VH1) holder).tv_collect.setText(data.getCollectTimes()+"");
            ImageUtil.loadLocalImage(((VH1) holder).iv,images.get(0));
        }else if(holder instanceof VH3){
            ((VH3) holder).tv_title.setText(data.getTitle());
            ((VH3) holder).tv_content.setText(data.getContent());
            ((VH3) holder).tv_author.setText(data.getUserName());
            ((VH3) holder).tv_date.setText(DateUtil.DateToString(data.getPushTime()));
            ((VH3) holder).tv_comment.setText(data.getCommentTimes()+"");
            ((VH3) holder).tv_collect.setText(data.getCollectTimes()+"");
            ((VH3) holder).tv_count.setText(images.size()+"图");
            ImageUtil.loadLocalImage(((VH3) holder).iv1,images.get(0));
            ImageUtil.loadLocalImage(((VH3) holder).iv2,images.get(1));
            ImageUtil.loadLocalImage(((VH3) holder).iv3,images.get(2));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        public VH(View itemView) {
            super(itemView);
        }
    }

    public class VH1 extends VH{

        ImageView iv;
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
        }
    }

    public class VH3 extends VH{

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
        }
    }
}
