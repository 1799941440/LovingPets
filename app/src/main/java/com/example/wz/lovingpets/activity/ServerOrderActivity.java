package com.example.wz.lovingpets.activity;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.base.BaseFragmentActivity;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.EventCodes;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ServiceOrderInfo;
import com.example.wz.lovingpets.fragment.ServerOrderFragment;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.DateUtil;
import com.example.wz.lovingpets.utils.EventBusUtils;
import com.example.wz.lovingpets.utils.UserUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

import static com.example.wz.lovingpets.activity.MyApp.getContext;
import static com.example.wz.lovingpets.common.EventCodes.CANCEL_SERVER_ORDER;
import static com.example.wz.lovingpets.common.EventCodes.DELAY_SERVER_ORDER;
import static com.example.wz.lovingpets.common.EventCodes.DEL_SERVER_ORDER;
import static com.example.wz.lovingpets.common.EventCodes.PAY_SERVER_ORDER;

@BindEventBus
public class ServerOrderActivity extends BaseFragmentActivity {

    private int userId;
    private ViewPager vp;
    private TabLayout tab;
    private TextView title;
    private ImageView iv_back;
    private List<ServerOrderFragment> list_view = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private List<String> list_title = Arrays.asList("全部","待受理","等待服务/服务中","待支付","服务取消","待评价","已评价");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_order);
        findViews();
        initData();
        getSO();
    }

    @Override
    protected void findViews() {
        vp = findViewById(R.id.server_order_vp);
        tab = findViewById(R.id.server_order_tab);
        title = findViewById(R.id.titlebar_tv_title);
        iv_back = findViewById(R.id.titlebar_iv_left);
    }

    @Override
    protected void initData() {
        userId = new UserUtil(getContext()).getUser().getId();
        title.setText("服务管理");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_view.add(ServerOrderFragment.newInstance(6));
        list_view.add(ServerOrderFragment.newInstance(0));
        list_view.add(ServerOrderFragment.newInstance(1));
        list_view.add(ServerOrderFragment.newInstance(2));
        list_view.add(ServerOrderFragment.newInstance(3));
        list_view.add(ServerOrderFragment.newInstance(4));
        list_view.add(ServerOrderFragment.newInstance(5));
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
    }

    private void getSO() {
        Observable<ListResponse<ServiceOrderInfo>> observable = api.getServerOrder(userId);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ServiceOrderInfo>>() {
            @Override
            public void onSuccess(ListResponse<ServiceOrderInfo> listResponse) {
                EventBusUtils.sendStickyEvent(new Event<List<ServiceOrderInfo>>(EventCodes.LOADED_SERVER_ORDER,listResponse.getRows()));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final Event<Integer> event){
        if(event.getCode() == CANCEL_SERVER_ORDER){
            byte state = 3;
            Observable<ListResponse> ob = api.cancelServerOrder(event.getData(),state);
            ObservableDecorator.decorate(ob, new ObservableDecorator.SuccessCall<ListResponse>() {
                @Override
                public void onSuccess(ListResponse listResponse) {
                    if(listResponse.isSuccess()){
                        showLongToast("取消成功");
                        getSO();
                    }
                }
            });
        }else if(event.getCode() == DELAY_SERVER_ORDER){
            delayOrder(event.getData());
        }else if(event.getCode() == DEL_SERVER_ORDER){
            Observable<ListResponse> ob = api.delServiceOrder(event.getData());
            ObservableDecorator.decorate(ob, new ObservableDecorator.SuccessCall<ListResponse>() {
                @Override
                public void onSuccess(ListResponse listResponse) {
                    if(listResponse.isSuccess()){
                        showLongToast("删除服务订单成功");
                        getSO();
                    }
                }
            });
        }else if(event.getCode() == PAY_SERVER_ORDER){
            Observable<ListResponse> ob = api.payServerOrder(event.getData());
            ObservableDecorator.decorate(ob, new ObservableDecorator.SuccessCall<ListResponse>() {
                @Override
                public void onSuccess(ListResponse listResponse) {
                    if(listResponse.isSuccess()){
                        showLongToast("支付服务订单成功");
                        getSO();
                    }
                }
            });
        }
    }

    private void delayOrder(final Integer id) {
        DatePickerDialog dp = new DatePickerDialog(ServerOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Observable<ListResponse> observable = api.delayServerOrder(id, year+"-"+(month+1)+"-"+dayOfMonth);
                ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse>() {
                    @Override
                    public void onSuccess(ListResponse listResponse) {
                        if(listResponse.isSuccess()){
                            showLongToast("延后服务成功");
                            getSO();
                        }
                    }
                });
            }
        },2018, 11, 18);
        dp.show();
    }
}
