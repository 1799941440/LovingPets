package com.example.wz.lovingpets.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.AddThemeActivity;
import com.example.wz.lovingpets.adapter.ThemeAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.ThemeInfo;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.net.HttpRequest;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@BindEventBus
public class CircleFragment extends BaseFragment {
    private View view;
    private int page = 1;
    private String mParam1;
    private String mParam2;
    private RecyclerView rv;
    private ImageView iv_add;
    private ThemeAdapter adapter;
    private RefreshLayout refreshLayout;
    private List<ThemeInfo> list = new ArrayList<>();
    public static final String TEXT_TITLE = "content";
    private HttpRequest.ApiService api = HttpRequest.getApiservice();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT_TITLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_layout_circle, container, false);
        view = rootView;
        findViews(view);
        initDatas();
        return rootView;
    }

    private void findViews(View view) {
        refreshLayout = view.findViewById(R.id.circle_refreshLayout);
        rv = view.findViewById(R.id.circle_rv);
        iv_add = view.findViewById(R.id.circle_addTheme);
    }

    private void initDatas() {
        adapter = new ThemeAdapter(list,getContext());
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        getTheme(page,refreshLayout);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getTheme(1,refreshlayout);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getTheme(page+1,refreshlayout);
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddThemeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getTheme(final int targetPage, final RefreshLayout refreshlayout){
        Observable<ListResponse<ThemeInfo>> observable = api.getTheme(targetPage);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<ThemeInfo>>() {
            @Override
            public void onSuccess(ListResponse<ThemeInfo> listResponse) {
                if(targetPage == 1){
                    list.clear();
                    refreshlayout.finishRefresh();
                }else{
                    refreshlayout.finishLoadMore();//传入false表示加载失败
                }
                if(listResponse.getTotal() != 10){
                    refreshlayout.setNoMoreData(true);
                }
                list.addAll(listResponse.getRows());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static CircleFragment newInstance(String param1) {
        CircleFragment fragment = new CircleFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
