package com.example.wz.lovingpets.ui.main_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.GoodsList;
import com.example.wz.lovingpets.activity.MainActivity;
import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.activity.ServiceListActivity;
import com.example.wz.lovingpets.adapter.ThemeAdapter;
import com.example.wz.lovingpets.adapter.TopGoodsAdapter;
import com.example.wz.lovingpets.adapter.TopServiceAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.GlideImageLoader;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ServiceInfo;
import com.example.wz.lovingpets.entity.ThemeInfo;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.fragment.CommonFragment;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

@BindEventBus
public class HomeFragment extends BaseFragment {
    public static final String TEXT_TITLE = "content";
    private String mParam1;
    private Banner banner;
    private int currentTheme,userId;
    private RecyclerView rv_server,rv_goods,rv_theme;
    private TopServiceAdapter adapter_service;
    private TopGoodsAdapter adapter_goods;
    private ThemeAdapter adapter_theme;
    private List<String> list = Arrays.asList("http://pjaorz5b8.bkt.clouddn.com/lunbo0.png",
            "http://pjaorz5b8.bkt.clouddn.com/lunbo1.png",
            "http://pjaorz5b8.bkt.clouddn.com/lunbo2.jpg");
    private List<String> list_title = Arrays.asList("加拿大进口 达福德Darford 无谷南瓜烘焙狗粮",
            "宠物驱虫专区",
            "带宠物看医生");
    private List<ServiceInfo> list_server = new ArrayList<>();
    private List<GoodsDetailInfo> list_goods = new ArrayList<>();
    private List<ThemeInfo> list_theme = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTheme = MyApp.getInstance().getCurrentTheme();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT_TITLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个if判断可以有效的防止页面切换造成的数据不显示的bug
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        view = rootView;
        findViews(view);
        initDatas();
        return rootView;
    }

    /**
     * 获取带参数的实例
     *
     * @param param1
     * @return
     */
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void findViews(View view) {
        banner = view.findViewById(R.id.home_banner);
        rv_server = view.findViewById(R.id.home_service_rv);
        rv_goods = view.findViewById(R.id.home_goods_rv);
        rv_theme = view.findViewById(R.id.home_theme_rv);
    }

    public void initDatas() {
        userId = new UserUtil(getContext()).getUser().getId();
        setBanner();
        adapter_service = new TopServiceAdapter(list_server,getContext());
        rv_server.setAdapter(adapter_service);
        adapter_goods = new TopGoodsAdapter(list_goods,getContext());
        rv_goods.setAdapter(adapter_goods);
        adapter_theme = new ThemeAdapter(list_theme,getContext());
        rv_theme.setAdapter(adapter_theme);
        rv_theme.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        getservice();
        getGoods();
        getTheme();
    }

    private void getTheme() {
        Observable<ListResponse<ThemeInfo>> observable = api.getTheme(1,4);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ThemeInfo>>() {
            @Override
            public void onSuccess(ListResponse<ThemeInfo> listResponse) {
                if(listResponse.isSuccess()){
                    list_theme.clear();
                    list_theme.addAll(listResponse.getRows());
                    adapter_theme.notifyDataSetChanged();
                }
            }
        });
    }

    private void getGoods() {
        Observable<ListResponse<GoodsDetailInfo>> observable = api.getTop4Goods(4,userId);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<GoodsDetailInfo>>() {
            @Override
            public void onSuccess(ListResponse<GoodsDetailInfo> listResponse) {
                if(listResponse.isSuccess()){
                    list_goods.clear();
                    list_goods.addAll(listResponse.getRows());
                    adapter_goods.notifyDataSetChanged();
                }
            }
        });
    }

    private void getservice() {
        Observable<ListResponse<ServiceInfo>> observable = api.getTop4Service(4);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ServiceInfo>>() {
            @Override
            public void onSuccess(ListResponse<ServiceInfo> listResponse) {
                if(listResponse.isSuccess()){
                    list_server.clear();
                    list_server.addAll(listResponse.getRows());
                    adapter_service.notifyDataSetChanged();
                }
            }
        });
    }

    private void setBanner() {
        banner.setImages(list);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerTitles(list_title);
        banner.setDelayTime(4000);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            Bundle b = new Bundle();
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        b.putString("condition",list_title.get(0));
                        b.putString("classify","进口狗粮");
                        intentWithData(GoodsList.class,b);
                        break;
                    case 1:
                        b.putString("condition","");
                        b.putString("classify","体外驱虫");
                        intentWithData(GoodsList.class,b);
                        break;
                    case 2:
                        Intent intent = new Intent(getContext(),ServiceListActivity.class);
                        intent.putExtra("type",1);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
