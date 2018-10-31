package com.example.wz.lovingpets.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wz.lovingpets.R;
import com.example.wz.lovingpets.adapter.SingleDialogAdapter;
import com.example.wz.lovingpets.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SingleDialog extends DialogFragment {

    private SingleDialogBuilder b;
    private TextView title;
    private List<String> list_item = new ArrayList<>();
    private SingleDialogAdapter adapter;
    private ListView lv;

    public SingleDialog() {
    }

    @SuppressLint("ValidFragment")
    public SingleDialog(SingleDialogBuilder b) {
        this.b = b;
    }

    private LayoutInflater inflater;
    public interface CallBack{
        void onItemClick(String target,int position);
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "SingleDialog");
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_singlecheck, null);
        initView(view);
        initBuilder();
        builder.setView(view);
        return builder.create();
    }

    private void initBuilder() {
        if(!StringUtils.isEmpty(b.getTitle())){
            title.setText(b.getTitle());
        }
        if(b.getList().size()>0){
            list_item.clear();
            list_item.addAll(b.getList());
            adapter = new SingleDialogAdapter(b.getList(),getContext());
            lv.setAdapter(adapter);
            setListViewHeight(lv);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(callBack == null){
                        throw new RuntimeException("you have not set a callBack for this");
                    }
                    callBack.onItemClick(list_item.get(position),position);
                    dismiss();
                }
            });
        }
    }

    private void initView(View view) {
        title = view.findViewById(R.id.dialog_single_title);
        lv = view.findViewById(R.id.dialog_single_lv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callBack = null;
    }
    private void setListViewHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter(); //得到ListView 添加的适配器
        if(listAdapter == null){
            return;
        }

        View itemView = listAdapter.getView(0, null, listView); //获取其中的一项
        //进行这一项的测量，为什么加这一步，具体分析可以参考 https://www.jianshu.com/p/dbd6afb2c890这篇文章
        itemView.measure(0,0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = listAdapter.getCount();//得到总的项数
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if(itemCount <= 5){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,itemHeight*itemCount*2);
        }else if(itemCount > 5){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,itemHeight*10);
        }
        listView.setLayoutParams(layoutParams);
    }
}
