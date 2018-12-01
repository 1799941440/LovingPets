package com.example.wz.lovingpets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.ShopCartAdapter;
import com.example.wz.lovingpets.base.BaseActivity;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DecimalUtil;
import com.example.wz.lovingpets.widget.ConfirmDialogBuilder;
import com.example.wz.lovingpets.widget.ViewDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

public class ShoppingCartActivity extends BaseFragmentActivity {

    private RecyclerView rv;
    private TextView tv_total;
    private CheckBox cb_all;
    private Button bt_pay;
    private String mParam1,mParam2;
    private ShopCartAdapter adapter;
    private ShopCartAdapter.CkeckBoxListener cbl;
    public static final String TEXT_TITLE = "content";
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private List<ShoppingCartDetail> list_shopcart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        findViews();
        initData();
    }

    @Override
    protected void findViews() {
        rv = findViewById(R.id.shopcart_rv);
        tv_total = findViewById(R.id.shopcart_tv_total);
        cb_all = findViewById(R.id.shopcart_cb_all);
        bt_pay = findViewById(R.id.shopcart_bt_pay);
    }

    @Override
    protected void initData() {
        getSC();
        cbl = new ShopCartAdapter.CkeckBoxListener() {
            @Override
            public void onCkeckboxSwitch(String amount) {
                tv_total.setText("￥" + amount);
            }
        };
        cb_all.setChecked(false);
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_all.isChecked()){
                    selectAll(list_shopcart,cb_all.isChecked());
                }else{
                    tv_total.setText("0.0");
                    adapter.setAmount("0.0");
                    unSelectAll(list_shopcart,cb_all.isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new ShopCartAdapter(list_shopcart,getContext(),cb_all);
        adapter.setCbl(cbl);
        rv.setAdapter(adapter);
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payForCart();
            }
        });
    }
    private void payForCart() {
        final String items = adapter.getSeletedItem();
        if(items.length()<1){
            showToast("你还没有选择结算的商品哦");
        }else{
            ViewDialogFragment dialog = new ConfirmDialogBuilder().setTv_title("确认支付？")
                    .setTv_left("稍后支付")
                    .setTv_right("立即支付")
                    .setTv_content("确认支付该订单吗？").build();
            dialog.setCallback(new ViewDialogFragment.Callback() {
                @Override
                public void onLeftClick() {
                    cartToOrder(items,0);
                }
                //支付
                @Override
                public void onRightClick() {
                    cartToOrder(items,2);
                }
            });
            dialog.show(getSupportFragmentManager());
        }
    }

    //发出购物车转换订单的请求
    private void cartToOrder(String items,Integer state) {
        Observable<ListResponse> observable = api.cartToOrder(items, MyApp.getInstance().getUser().getId(),state);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                showLongToast(listResponse.getMsg());
                getSC();
                tv_total.setText("0.0");
            }
        });
    }

    private void unSelectAll(List<ShoppingCartDetail> list,boolean b) {
        for(ShoppingCartDetail s :list){
            s.setSelected(b);
        }
    }

    private void selectAll(List<ShoppingCartDetail> list,boolean b) {
        String total = "0.0";
        for(ShoppingCartDetail s :list){
            total = DecimalUtil.add(total,String.valueOf(s.getTotal()));
            s.setSelected(b);
        }
        tv_total.setText(total);
        adapter.setAmount(total);
    }

    private void getSC(){
        Observable<ListResponse<ShoppingCartDetail>> observable = api.getSC(MyApp.getInstance().getUser().getShoppingcartId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ShoppingCartDetail>>() {
            @Override
            public void onSuccess(ListResponse<ShoppingCartDetail> listResponse) {
                loadDataSuccess(listResponse);
            }
        });
    }

    private void loadDataSuccess(ListResponse<ShoppingCartDetail> listResponse) {
        if(adapter == null){
            adapter = new ShopCartAdapter(listResponse.getRows(),getApplicationContext(),cb_all);
            adapter.setCbl(cbl);
            rv.setAdapter(adapter);
        }else{
            list_shopcart.clear();
            list_shopcart.addAll(listResponse.getRows());
            adapter.notifyDataSetChanged();
        }
    }

}
