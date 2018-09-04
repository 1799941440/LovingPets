package com.example.wz.lovingpets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.activity.MyApp;
import com.example.wz.lovingpets.adapter.LeftRecyclerAdapter;
import com.example.wz.lovingpets.adapter.RightRecyclerAdapter;
import com.example.wz.lovingpets.base.BaseFragment;
import com.example.wz.lovingpets.common.BindEventBus;
import com.example.wz.lovingpets.common.Constant;
import com.example.wz.lovingpets.common.Event;
import com.example.wz.lovingpets.entity.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@BindEventBus
public class ClassifyFragment extends BaseFragment {
    public static final String TEXT_TITLE = "content";
    private String mParam1;
    private String mParam2;
    private TextView mTextView;
    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private RecyclerView.LayoutManager leftLayoutManager;
    private RecyclerView.LayoutManager rightLayoutManager;
    private LeftRecyclerAdapter leftAdapter;
    private RightRecyclerAdapter rightAdapter;
    private List<String> bigSortList = new ArrayList<String>();
    private int currentTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TEXT_TITLE);
        }
        currentTheme = MyApp.getInstance().getCurrentTheme();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        findViews(view);
        initDatas();
        return view;
    }

    private void initDatas() {
        bigSortList.addAll(Constant.classifyList.get(currentTheme));
    }

    private void findViews(View view) {
        leftRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sort_left) ;
        rightRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sort_right);
        leftLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        leftRecyclerView.setLayoutManager(leftLayoutManager);
        leftAdapter = new LeftRecyclerAdapter(getActivity(), bigSortList, leftRecyclerView);
        //左侧列表的点击事件
        leftAdapter.setItemClickListener(new LeftRecyclerAdapter.LeftListener() {
            @Override
            public void onItemClick(int position) {
                //向适配器中返回点击的位置，在适配器中进行操作
                leftAdapter.getSelectedPosition(position);
                rightAdapter.getSelectedPosition(position);
            }
        });
        leftRecyclerView.setAdapter(leftAdapter);


        rightLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rightRecyclerView.setLayoutManager(rightLayoutManager);
        rightAdapter = new RightRecyclerAdapter(getActivity(), bigSortList,getTexts(), rightRecyclerView,getImages());
        //右侧列表的点击事件
        rightAdapter.setItemClickListener(new RightRecyclerAdapter.RightListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),bigSortList.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        //右侧列表的滚动事件
        rightRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取右侧列表的第一个可见Item的position
                int TopPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //左侧得到这个position
                leftAdapter.getSelectedPosition(TopPosition);
            }
        });
        rightRecyclerView.setAdapter(rightAdapter);

    }

    public static ClassifyFragment newInstance(String param1) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private List<List<Integer>> getImages(){
        if(currentTheme == 3){
            return Constant.classify_iv_fish;
        }else if(currentTheme == 2){
            return Constant.classify_iv_bird;
        }else if(currentTheme == 1){
            return Constant.classify_iv_cat;
        }else{
            return Constant.classify_iv_dog;
        }
    }

    private List<List<String>> getTexts(){
        if(currentTheme == 3){
            return Constant.classify_tv_fish;
        }else if(currentTheme == 2){
            return Constant.classify_tv_bird;
        }else if(currentTheme == 1){
            return Constant.classify_tv_cat;
        }else{
            return Constant.classify_tv_dog;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(Event<User> event){

    }
}
