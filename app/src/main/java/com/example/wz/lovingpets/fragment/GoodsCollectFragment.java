package com.example.wz.lovingpets.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.CollectGoodsAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.ObservableDecorator;
import com.example.wz.lovingpets.entity.CollectGoodsInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.net.HttpRequest;
import com.example.wz.lovingpets.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GoodsCollectFragment extends BaseFragment {
    private View view;
    private Integer userId;
    private RecyclerView rv;
    private CollectGoodsAdapter adapter;
    private List<CollectGoodsInfo> list = new ArrayList<>();
    private HttpRequest.ApiService api = HttpRequest.getApiservice();

    public GoodsCollectFragment() {

    }

    /**
     *
     */
    public static GoodsCollectFragment newInstance() {
        GoodsCollectFragment fragment = new GoodsCollectFragment();
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
        getCG();
        return rootView;
    }

    private void initDatas() {
        userId = new UserUtil(getContext()).getUser().getId();
    }

    private void findViews(View view) {
        rv = view.findViewById(R.id.collect_rv);
        adapter = new CollectGoodsAdapter(getContext(),list);
        rv.setAdapter(adapter);
    }

    private void getCG() {
        Observable<ListResponse<CollectGoodsInfo>> observable = api.getCollectGoods(userId,0);
        ObservableDecorator.decorate(observable, new ObservableDecorator.SuccessCall<ListResponse<CollectGoodsInfo>>() {
            @Override
            public void onSuccess(ListResponse<CollectGoodsInfo> listResponse) {
                list.clear();
                list.addAll(listResponse.getRows());
                adapter.notifyDataSetChanged();
            }
        });
    }

}
