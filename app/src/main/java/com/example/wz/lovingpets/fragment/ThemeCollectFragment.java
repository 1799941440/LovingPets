package com.example.wz.lovingpets.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.CollectThemeAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.CollectThemeInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ThemeCollectFragment extends BaseFragment {
    private View view;
    private Integer userId;
    private RecyclerView rv;
    private List<CollectThemeInfo> list = new ArrayList<>();
    private CollectThemeAdapter adapter;

    public ThemeCollectFragment() {
        // Required empty public constructor
    }

    /**
     *
     */
    public static ThemeCollectFragment newInstance() {
        ThemeCollectFragment fragment = new ThemeCollectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_goods_collect, container, false);
        view = rootView;
        findViews(view);
        initDatas();
        getCT();
        return rootView;
    }

    private void getCT() {
        Observable<ListResponse<CollectThemeInfo>> observable = HttpRequest.getApiservice().getCollectTheme(userId,1);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<CollectThemeInfo>>() {
            @Override
            public void onSuccess(ListResponse<CollectThemeInfo> listResponse) {
                list.clear();
                list.addAll(listResponse.getRows());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initDatas() {
        userId = new UserUtil(getContext()).getUser().getId();
        adapter = new CollectThemeAdapter(getContext(),list);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void findViews(View view) {
        rv = view.findViewById(R.id.collect_rv);
    }

}
