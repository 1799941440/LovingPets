package com.example.wz.lovingpets.ui.order;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.OrderInfo;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.fragment.OrderFragment;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.UserUtil;
import com.example.wz.lovingpets.widget.ConfirmDialog;
import com.example.wz.lovingpets.widget.ConfirmDialogBuilder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;

@BindEventBus
public class OrderActivity extends BaseFragmentActivity{

    private User user;
    private ViewPager vp;
    private TabLayout tab;
    private TextView title;
    private int currentItem ;
    private ConfirmDialog dialog;
    private List<OrderFragment> list_view = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private List<String> list_title = Arrays.asList("全部","订单失效","待付款","待收货","交易成功");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findViews();
        initData();
        getOrder();
    }

    protected void findViews() {
        tab = findViewById(R.id.order_tab);
        title = findViewById(R.id.titlebar_tv_title);
        vp = findViewById(R.id.order_vp);
    }

    protected void initData() {
        user = new UserUtil(getContext()).getUser();
        title.setText("订单管理");
        currentItem = getIntent().getBundleExtra("bundle").getInt("orderState",0);
        list_view.add(OrderFragment.newInstance(4));
        list_view.add(OrderFragment.newInstance(1));
        list_view.add(OrderFragment.newInstance(0));
        list_view.add(OrderFragment.newInstance(2));
        list_view.add(OrderFragment.newInstance(3));
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_view.get(position);
            }
            @Override
            public int getCount() {
                return list_view.size();
            }
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list_title.get(position);
            }
        });
        vp.setOffscreenPageLimit(list_view.size());
        vp.setCurrentItem(currentItem);
    }
    public void getOrder(){
        Observable<ListResponse<OrderInfo>> observable = api.getOrder(user.getId());
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<OrderInfo>>() {
            @Override
            public void onSuccess(ListResponse<OrderInfo> listResponse) {
                if(listResponse.isSuccess()){
                    EventBusUtils.sendStickyEvent(new Event<List<OrderInfo>>(EventCodes.LOADED_ORDER,listResponse.getRows()));
                }else {
                    showToast("获取失败");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startGet(final Event<List> event){
        if(event.getCode() == EventCodes.DEL_ORDER){
            showDelDialog(event);
        }else if(event.getCode() == EventCodes.PAY_ORDER){
            showPayDialog(event);
        }else if(event.getCode() == EventCodes.RECEIVE_ORDER){
            showRecDialog(event);
        }
    }

    private void showDelDialog(final Event<List> event) {
        dialog= new ConfirmDialogBuilder()
                .setTv_title("删除订单")
                .setTv_content("确认删除订单编号为"+event.getData().get(0)+"的订单吗？")
                .build();
        dialog.setCallback(new ConfirmDialog.Callback() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                delOrder((Integer) event.getData().get(0));
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showPayDialog(final Event<List> event) {
        dialog= new ConfirmDialogBuilder()
                .setTv_title("确认支付？")
                .setTv_content("确认支付订单编号为"+event.getData().get(0)+"的订单吗？")
                .build();
        dialog.setCallback(new ConfirmDialog.Callback() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                payOrder(event.getData());
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    private void showRecDialog(final Event<List> event) {
        dialog= new ConfirmDialogBuilder()
                .setTv_title("确认收货？")
                .setTv_content("确认编号为"+event.getData().get(0)+"的订单到货了吗？")
                .build();
        dialog.setCallback(new ConfirmDialog.Callback() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                recOrder(event.getData());
            }
        });
        dialog.show(getSupportFragmentManager());
    }

    @SuppressLint("CheckResult")
    private void payOrder(List<Integer> data) {
        Observable<ListResponse> observable = api.changeOrderState(data.get(0),data.get(1));
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showToast("支付成功！");
                    getOrder();
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void recOrder(List<Integer> data) {
        Observable<ListResponse> observable = api.changeOrderState(data.get(0),data.get(1));
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                if(listResponse.isSuccess()){
                    showToast("收货成功！");
                    getOrder();
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void delOrder(Integer data) {
        Observable<ListResponse> observable = api.delOrder(data);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
            @Override
            public void onSuccess(ListResponse listResponse) {
                showToast(listResponse.getMsg());
                getOrder();
            }
        });
    }
}
