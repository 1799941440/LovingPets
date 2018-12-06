package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.AddPetActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.PetInfo;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.google.gson.Gson;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {
    private Gson gson;
    private Context context;
    private List<PetInfo> list;
    private boolean shouldIntent = true;
    private LayoutInflater layoutInflater;
    private OnPetSelected onPetSelected;

    public void setOnPetSelected(OnPetSelected onPetSelected) {
        this.onPetSelected = onPetSelected;
    }

    public interface OnPetSelected{
        void onSelected(int petId,String icon,String name);
    }
    public PetAdapter(List<PetInfo> list, Context context) {
        this.list = list;
        this.context = context;
        gson = new Gson();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public PetAdapter(List<PetInfo> list, Context context,boolean shouldIntent) {
        this.list = list;
        gson = new Gson();
        this.context = context;
        this.shouldIntent = shouldIntent;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PetHolder(layoutInflater.inflate(R.layout.item_pet,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PetHolder holder, int position) {
        final PetInfo data = list.get(position);
        if(shouldIntent){
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddPetActivity.class);
                    Bundle b = new Bundle();
                    b.putString("petInfo",gson.toJson(data));
                    intent.putExtra("bundle",b);
                    context.startActivity(intent);
                }
            });
            holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    EventBusUtils.sendEvent(new Event<Integer>(EventCodes.DEL_PET,data.getId()));
                    return true;
                }
            });
        }else{
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPetSelected.onSelected(data.getId(),data.getIcon(),data.getNickName());
                }
            });
        }
        holder.tv_name.setText(data.getNickName());
        holder.tv_class.setText("("+data.getClassName()+")");
        ImageUtil.loadRoundImage(holder.iv_head,data.getIcon());
        ImageUtil.loadLocalImage(holder.iv_sex,data.getSex().equals("公")?R.drawable.ic_male:R.drawable.ic_female);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PetHolder extends RecyclerView.ViewHolder{
        public LinearLayout ll;
        public TextView tv_name,tv_class;
        public ImageView iv_head,iv_sex;
        public PetHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.item_pet_name);
            tv_class = view.findViewById(R.id.item_pet_class);
            iv_head = view.findViewById(R.id.item_pet_head);
            iv_sex = view.findViewById(R.id.item_pet_sex);
            ll = view.findViewById(R.id.item_pet_ll);
        }
    }
}
