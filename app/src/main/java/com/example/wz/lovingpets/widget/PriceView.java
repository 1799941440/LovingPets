package com.example.wz.lovingpets.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.utils.UIUtils;

public class PriceView extends LinearLayout {

    private Context mContext;
    private float price;
    private int price_int;
    private TextView tv_priceType,tv_priceInt,tv_priceDec;
    private String price_type,price_dec;

    public PriceView(Context context) {
        this(context,null);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PriceView);
        initAttrs(ta); //初始化属性
        ta.recycle();
        init();//初始化相关操作
    }

    private void init() {

        setOrientation(HORIZONTAL);
        View view = initView();
        setData();
//        tv_priceDec.setTextSize(TypedValue.COMPLEX_UNIT_PX,UIUtils.sp2px(mContext,10f));
        addView(view);
    }

    private void setData() {
        tv_priceType.setText(price_type == null ? "￥":price_type);
        tv_priceInt.setText(price_int+"");
        tv_priceDec.setText(price_dec);
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.layout_price_view, null);
        tv_priceType = view.findViewById(R.id.price_type);
        tv_priceInt = view.findViewById(R.id.price_integer);
        tv_priceDec = view.findViewById(R.id.price_decimal);
        return view;
    }

    private void initAttrs(TypedArray ta) {
        price = ta.getFloat(R.styleable.PriceView_price,0);
        price_type = ta.getString(R.styleable.PriceView_price_type);
        formatPrice();
    }

    private void formatPrice() {
        price_int = (int)Math.floor(price);
        price_dec = DecimalUtil.subtract(String.valueOf(price),String.valueOf(price_int));
        price_dec = price_dec.substring(1,price_dec.length());
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
        formatPrice();
        setData();
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }
}
