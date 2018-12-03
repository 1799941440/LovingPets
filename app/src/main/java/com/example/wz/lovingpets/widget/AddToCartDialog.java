package com.example.wz.lovingpets.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.ConfirmOrderActivity;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.ImageUtil;
import com.example.wz.lovingpets.utils.UserUtil;
import com.google.gson.Gson;

import java.util.Arrays;

public class AddToCartDialog extends DialogFragment {

    private Integer SCId,position;
    private PriceView priceView;
    private GoodsDetailInfo data;
    private LayoutInflater inflater;
    private NumberPickerView number;
    private Button bt_add_cart,bt_pay;
    private ImageView iv_image, iv_collect;
    private TextView tv_goodsName, tv_count, tv_sales, tv_shopName, tv_classify;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        SCId = new UserUtil(getContext()).getUser().getShoppingcartId();
        Bundle bundle = getArguments();
        if (bundle != null) {
            data = new Gson().fromJson(bundle.getString("goodsInfo"), GoodsDetailInfo.class);
            position = bundle.getInt("position");
        }
        final View view = inflater.inflate(R.layout.dialog_add_to_cart, null);
        initView(view);
        setData();
        builder.setView(view);
        return builder.create();
    }

    private void setData() {
        tv_classify.setText(data.getClassify());
        tv_shopName.setText(data.getShopName());
        tv_sales.setText("售出：" + data.getSales());
        tv_count.setText("库存：" + data.getCount());
        tv_goodsName.setText(data.getName());
        priceView.setPrice(data.getPrice());
        priceView.setPrice_type("￥");
        ImageUtil.loadNetImage(iv_image,data.getImage());
        ImageUtil.loadLocalImage(iv_collect,data.getIsCollect() == 0
                ? R.drawable.ic_my_collection:R.drawable.ic_collected);
        number.setMaxValue(40 >= data.getCount() ? data.getCount() : 40) //最大输入值，也就是限量，默认无限大
                .setCurrentInventory(data.getCount()) // 当前的库存
                .setMinDefaultNum(1)  // 最小限定量
                .setCurrentNum(1);  // 当前数量
        iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event<Integer>(EventCodes.SWITCH_COLLECT,position));
                if(data.getIsCollect() == 0){
                    data.setIsCollect(1);
                }else{
                    data.setIsCollect(0);
                }
                ImageUtil.loadLocalImage(iv_collect,data.getIsCollect() == 0
                        ? R.drawable.ic_my_collection:R.drawable.ic_collected);
            }
        });
        bt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer count = number.getNumText();
                ShoppingCartDetail s = new ShoppingCartDetail(0, SCId, data.getId(), count,
                        data.getPrice(), data.getPrice() * count, data.getShopId());
                EventBusUtils.sendEvent(new Event<ShoppingCartDetail>(EventCodes.ADD_TO_CART, s));
                dismiss();
            }
        });
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmOrderActivity.class);
                intent.putExtra("count",number.getNumText());
                intent.putExtra("data",new Gson().toJson(data));
                intent.putExtra("type",1);
                startActivity(intent);
//                EventBusUtils.sendEvent(new Event<String>(EventCodes.BUY_GOODS, data.getId()+" "+count));
                dismiss();
            }
        });
    }

    private void initView(View view) {
        priceView = view.findViewById(R.id.goodsdetail_price);
        number = view.findViewById(R.id.goodsdetail_number_picker);
        bt_add_cart = view.findViewById(R.id.goodsdetail_add_to_cart);
        bt_pay = view.findViewById(R.id.goodsdetail_pay);
        iv_image = view.findViewById(R.id.goodsdetail_image);
        iv_collect = view.findViewById(R.id.goodsdetail_isCollect);
        tv_goodsName = view.findViewById(R.id.goodsdetail_goodsName);
        tv_count = view.findViewById(R.id.goodsdetail_count);
        tv_sales = view.findViewById(R.id.goodsdetail_sales);
        tv_shopName = view.findViewById(R.id.goodsdetail_shopName);
        tv_classify = view.findViewById(R.id.goodsdetail_classify);
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "AddToCartDialog");
    }
}
