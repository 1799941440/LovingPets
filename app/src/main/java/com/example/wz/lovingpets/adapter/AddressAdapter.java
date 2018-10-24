package com.example.wz.lovingpets.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.EditAddressActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.Address;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.google.gson.Gson;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Address> list;
    private LayoutInflater layoutInflater;

    public AddressAdapter(Context context, List<Address> list) {
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_address,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Address data = list.get(position);
        ((Holder)holder).tv_receiver.setText(data.getReceiver());
        ((Holder)holder).tv_contact.setText(data.getContact());
        ((Holder)holder).tv_address.setText(appendAddress(data));
        ((Holder)holder).cb.setChecked(data.getIsCommonAddress() == 1);
        ((Holder)holder).ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra("address",new Gson().toJson(data));
                context.startActivity(intent);
            }
        });
    }

    private String appendAddress(Address data) {
        String s = "";
        s+=(data.getProvince()+"-");
        s+=(data.getCity()+"-");
        s+=data.getFullAddress();
        return s.toString();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class Holder extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private CheckBox cb;
        private TextView tv_receiver,tv_contact,tv_address;
        public Holder(View view) {
            super(view);
            ll = view.findViewById(R.id.address_ll_root);
            tv_receiver = view.findViewById(R.id.address_receiver);
            tv_contact = view.findViewById(R.id.address_contact);
            tv_address = view.findViewById(R.id.address_fullAddress);
            cb = view.findViewById(R.id.address_cb);
        }
    }
}
